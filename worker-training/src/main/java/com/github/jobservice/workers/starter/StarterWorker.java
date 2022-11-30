package com.github.jobservice.workers.starter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpe.caf.api.Codec;
import com.hpe.caf.api.CodecException;
import com.hpe.caf.api.worker.InvalidTaskException;
import com.hpe.caf.api.worker.TaskRejectedException;
import com.hpe.caf.api.worker.TaskStatus;
import com.hpe.caf.api.worker.Worker;
import com.hpe.caf.api.worker.WorkerResponse;
import com.hpe.caf.api.worker.WorkerTaskData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class StarterWorker implements Worker {
    private static final String WORKER_IDENTIFIER = StarterWorker.class.getSimpleName();
    private static final int WORKER_API_VERSION = 1;
    private final WorkerTaskData workerTaskData;
    private final String errorQueue;
    private final Codec codec;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private enum WORKER_STATE {START,END,FAIL;}
    private static final String PLAYER_ONE = "Ready Player 1";
    private static final String PLAYER_TWO = "Ready Player 2";
    private static final String PLAYER_THREE = "Ready Player 3";
    private static final String PLAYER_FAIL = "What Player";
    private static final Logger LOGGER = LoggerFactory.getLogger(StarterWorker.class);
    public StarterWorker(final WorkerTaskData workerTaskData,
                         final String errorQueue,
                         final Codec codec) {
        LOGGER.info("Hello Starter Worker!");
        this.workerTaskData = workerTaskData;
        this.errorQueue = errorQueue;
        this.codec = codec;
    }

    @Override
    public WorkerResponse doWork() throws InterruptedException, TaskRejectedException, InvalidTaskException {

        LOGGER.info("Received request to Stater Worker");
        final byte[] data = workerTaskData.getData();

        final String message = new String(data, StandardCharsets.UTF_8);
        LOGGER.info(String.format("Message to Stater Worker <%s>", message));

        final StarterWorkerPojo workerData;
        try {
            workerData = OBJECT_MAPPER.readValue(workerTaskData.getData(), StarterWorkerPojo.class);
        } catch (IOException e) {
            LOGGER.error(String.format("Error with Stater Worker <%s>", message), e);
            throw new RuntimeException(e);
        }

        try {
            return determineResponse(workerData);
        } catch (CodecException e) {
            LOGGER.error(String.format("Error in Stater Worker response <%s>", message), e);
            throw new RuntimeException(e);
        }
    }

    private WorkerResponse determineResponse(final StarterWorkerPojo workerData) throws CodecException {
        final WorkerResponse response;

        try {
            switch(workerData.getMessage()) {
                case PLAYER_ONE : {
                    workerData.setMessage(PLAYER_TWO);
                    response = buildResponse(WORKER_STATE.START, workerData);
                    break;
                }
                case PLAYER_TWO : {
                    workerData.setMessage(PLAYER_THREE);
                    response = buildResponse(WORKER_STATE.START, workerData);
                    break;
                }
                default : {
                    workerData.setMessage(PLAYER_FAIL);
                    response = buildResponse(WORKER_STATE.FAIL, workerData);
                    break;
                }
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    private WorkerResponse buildResponse(final WORKER_STATE state, final StarterWorkerPojo pojo) throws JsonProcessingException {
        final WorkerResponse response;
        final byte[] data = OBJECT_MAPPER.writeValueAsString(pojo).getBytes();
        switch(state) {
            case START : {
                response = new WorkerResponse(workerTaskData.getTo(), TaskStatus.NEW_TASK, data, WORKER_IDENTIFIER, WORKER_API_VERSION, null);
                break;
            }
            case END : {
                response = new WorkerResponse(workerTaskData.getTo(), TaskStatus.RESULT_SUCCESS, data, WORKER_IDENTIFIER, WORKER_API_VERSION, null);
                break;
            }
            default : {
                response = new WorkerResponse(errorQueue, TaskStatus.RESULT_FAILURE, data, WORKER_IDENTIFIER, WORKER_API_VERSION, null);
                break;
            }
        };
        return response;
    }

    @Override
    public String getWorkerIdentifier() {
        return WORKER_IDENTIFIER;
    }

    @Override
    public int getWorkerApiVersion() {
        return WORKER_API_VERSION;
    }

    @Override
    public WorkerResponse getGeneralFailureResult(Throwable throwable) {
        return new WorkerResponse(
                errorQueue,
                TaskStatus.RESULT_EXCEPTION,
                throwable.getMessage().getBytes(),
                WORKER_IDENTIFIER,
                WORKER_API_VERSION,
                null);
    }
}
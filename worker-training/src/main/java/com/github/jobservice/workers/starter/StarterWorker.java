package com.github.jobservice.workers.starter;

import com.hpe.caf.api.Codec;
import com.hpe.caf.api.worker.InvalidTaskException;
import com.hpe.caf.api.worker.TaskRejectedException;
import com.hpe.caf.api.worker.TaskStatus;
import com.hpe.caf.api.worker.Worker;
import com.hpe.caf.api.worker.WorkerResponse;
import com.hpe.caf.api.worker.WorkerTaskData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StarterWorker implements Worker {
    private static final String WORKER_IDENTIFIER = StarterWorker.class.getSimpleName();
    private static final int WORKER_API_VERSION = 1;
    private final WorkerTaskData workerTaskData;
    private final String errorQueue;
    private final Codec codec;
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
        byte[] data = workerTaskData.getData();
        return new WorkerResponse(null, TaskStatus.RESULT_SUCCESS, new byte[]{}, WORKER_IDENTIFIER, WORKER_API_VERSION, null);
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
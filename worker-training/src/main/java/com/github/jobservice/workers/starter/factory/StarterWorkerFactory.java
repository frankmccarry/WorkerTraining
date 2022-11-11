package com.github.jobservice.workers.starter.factory;

import com.github.jobservice.workers.starter.StarterWorker;
import com.hpe.caf.api.Codec;
import com.hpe.caf.api.ConfigurationException;
import com.hpe.caf.api.ConfigurationSource;
import com.hpe.caf.api.HealthResult;
import com.hpe.caf.api.worker.BulkWorker;
import com.hpe.caf.api.worker.BulkWorkerRuntime;
import com.hpe.caf.api.worker.DataStore;
import com.hpe.caf.api.worker.DivertedTaskAction;
import com.hpe.caf.api.worker.DivertedTaskHandler;
import com.hpe.caf.api.worker.InvalidTaskException;
import com.hpe.caf.api.worker.JobStatus;
import com.hpe.caf.api.worker.TaskInformation;
import com.hpe.caf.api.worker.TaskMessage;
import com.hpe.caf.api.worker.TaskRejectedException;
import com.hpe.caf.api.worker.Worker;
import com.hpe.caf.api.worker.WorkerCallback;
import com.hpe.caf.api.worker.WorkerException;
import com.hpe.caf.api.worker.WorkerFactory;
import com.hpe.caf.api.worker.WorkerTaskData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

public class StarterWorkerFactory implements WorkerFactory, DivertedTaskHandler, BulkWorker {
    private final StarterWorkerConfiguration configuration;
    private final String errorQueue;
    private final Codec codec;
    private static final Logger LOGGER = LoggerFactory.getLogger(StarterWorkerFactory.class);

    @Override
    public Worker getWorker(WorkerTaskData workerTask) throws TaskRejectedException, InvalidTaskException {
        return new StarterWorker(workerTask, errorQueue, codec);
    }

    public StarterWorkerFactory(final ConfigurationSource configSource, final DataStore dataStore, final Codec codec) throws WorkerException {
        try {
            this.configuration = configSource.getConfiguration(StarterWorkerConfiguration.class);
        } catch (final ConfigurationException e) {
            throw new WorkerException("Failed to load Task Stowing Worker configuration", e);
        }
        this.errorQueue = configuration.getFailureQueue();
        this.codec = Objects.requireNonNull(codec);
    }
    @Override
    public String getInvalidTaskQueue() {
        return configuration.getFailureQueue();
    }

    @Override
    public int getWorkerThreads() {
        return configuration.getThreads();
    }

    @Override
    public HealthResult healthCheck() {
        return HealthResult.RESULT_HEALTHY;
    }

    @Override
    public DivertedTaskAction handleDivertedTask(TaskMessage taskMessage, TaskInformation taskInformation, boolean b, Map<String, Object> map, Codec codec, JobStatus jobStatus, WorkerCallback workerCallback) {
        return jobStatus == JobStatus.Paused
                ? DivertedTaskAction.Execute
                : DivertedTaskAction.Forward;
    }

    @Override
    public void processTasks(BulkWorkerRuntime bulkWorkerRuntime) throws InterruptedException {
        final long maxBatchTime = configuration.getMaxBatchTime();
        final int maxBatchSize = configuration.getMaxBatchSize();
        final long cutoffTime = System.currentTimeMillis() + maxBatchTime;

        LOGGER.debug("Starting to collect tasks for bulk processing. Max batch size: {}. Max batch time: {}. Cut-off time: {}",
                maxBatchSize, maxBatchTime, cutoffTime);
    }
}

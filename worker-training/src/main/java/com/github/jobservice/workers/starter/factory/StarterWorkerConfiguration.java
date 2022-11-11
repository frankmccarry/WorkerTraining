package com.github.jobservice.workers.starter.factory;

import com.hpe.caf.api.worker.WorkerConfiguration;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StarterWorkerConfiguration extends WorkerConfiguration {

    @NotNull
    @Size(min = 1)
    private String outputQueue;

    @NotNull
    @Size(min = 1)
    private String failureQueue;

    @Min(1)
    @Max(20)
    private int threads;

    @NotNull
    private long maxBatchTime;

    @Min(1)
    private int maxBatchSize;

    public long getMaxBatchTime() {
        return maxBatchTime;
    }

    public void setMaxBatchTime(long maxBatchTime) {
        this.maxBatchTime = maxBatchTime;
    }

    public int getMaxBatchSize() {
        return maxBatchSize;
    }

    public void setMaxBatchSize(int maxBatchSize) {
        this.maxBatchSize = maxBatchSize;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public String getOutputQueue() {
        return outputQueue;
    }

    public void setOutputQueue(String outputQueue) {
        this.outputQueue = outputQueue;
    }

    public String getFailureQueue() {
        return failureQueue;
    }

    public void setFailureQueue(String failureQueue) {
        this.failureQueue = failureQueue;
    }
}

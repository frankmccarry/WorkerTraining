
({
    workerName: "worker-starter",
    workerVersion: "1.0.0-SNAPSHOT",
    outputQueue: getenv("CAF_WORKER_OUTPUT_QUEUE") || undefined,
    failureQueue: getenv("CAF_WORKER_FAILURE_QUEUE") ||
        (getenv("CAF_WORKER_BASE_QUEUE_NAME") || getenv("CAF_WORKER_NAME") || "worker") + "-err",
    threads: getenv("CAF_WORKER_THREADS") || 1,
    maxBatchTime: getenv("CAF_WORKER_MAX_BATCH_TIME") || 180000,
    maxBatchSize: getenv("CAF_WORKER_MAX_BATCH_SIZE") || 100
});
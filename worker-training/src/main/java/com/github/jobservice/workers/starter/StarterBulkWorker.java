package com.github.jobservice.workers.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StarterBulkWorker {
    public static final String WORKER_IDENTIFIER = StarterBulkWorker.class.getSimpleName();
    public static final int WORKER_API_VERSION = 1;
    private static final Logger LOGGER = LoggerFactory.getLogger(StarterBulkWorker.class);
}

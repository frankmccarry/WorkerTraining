package com.github.jobservice.workers.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StarterWorkerPojo {
    private static final Logger LOGGER = LoggerFactory.getLogger(StarterWorkerPojo.class);

    private final String message;

    StarterWorkerPojo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

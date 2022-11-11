package com.github.jobservice.workers.starter.factory;

import com.hpe.caf.api.Codec;
import com.hpe.caf.api.ConfigurationSource;
import com.hpe.caf.api.worker.DataStore;
import com.hpe.caf.api.worker.WorkerException;
import com.hpe.caf.api.worker.WorkerFactory;
import com.hpe.caf.api.worker.WorkerFactoryProvider;

public class StarterWorkerFactoryProvider implements WorkerFactoryProvider {
    @Override
    public WorkerFactory getWorkerFactory(ConfigurationSource configurationSource, DataStore dataStore, Codec codec) throws WorkerException {
        return new StarterWorkerFactory(configurationSource, dataStore, codec);
    }
}

package org.simpledfs.core.excutor;

import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;

import java.util.Map;
import java.util.concurrent.*;

public class DefaultActuator<T> implements Actuator<T> {

    private Map<String, Processor> processorHolder = new ConcurrentHashMap<>();

    private Executor executor;

    public DefaultActuator() {
        executor = new ThreadPoolExecutor(
                10,
                20,
                5,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new DefaultThreadFactory("event-executor-pool", true));
    }

    @Override
    public T execute(Object... request) {
        return null;
    }

}

package org.simpledfs.core.excutor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.req.Request;

import java.util.concurrent.*;

public class DefaultActuator<T> implements Actuator<T> {

    private Executor executor;

    public DefaultActuator() {
        executor = new ThreadPoolExecutor(
                10,
                20,
                5,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new DefaultThreadFactory("packet-executor-pool", true));
    }

    @Override
    public void execute(ChannelHandlerContext ctx, Request request, Context context, Long packetId) {
        Processor processor = request.buildSelfProcessor(ctx, request, context, packetId);
        executor.execute(processor);
    }

}

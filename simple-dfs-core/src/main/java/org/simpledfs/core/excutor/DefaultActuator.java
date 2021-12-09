package org.simpledfs.core.excutor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.simpledfs.core.packet.Packet;
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
    public void execute(ChannelHandlerContext ctx, Request request, Object... params) {
        Processor processor = request.buildSelfProcessor(ctx, request, params);
        executor.execute(processor);
    }

}

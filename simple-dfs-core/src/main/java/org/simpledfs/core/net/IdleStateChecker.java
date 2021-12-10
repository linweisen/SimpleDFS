package org.simpledfs.core.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class IdleStateChecker extends IdleStateHandler {

    private static Logger LOGGER = LogManager.getLogger(IdleStateChecker.class);

    private static final int DEFAULT_READER_IDLE_TIME = 15;

    private int readerTime;

    public IdleStateChecker(int readerIdleTime) {
        super(readerIdleTime == 0 ? DEFAULT_READER_IDLE_TIME : readerIdleTime, 0, 0, TimeUnit.SECONDS);
        readerTime = readerIdleTime == 0 ? DEFAULT_READER_IDLE_TIME : readerIdleTime;
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        LOGGER.warn("[{}] Hasn't read data after {} seconds, will close the channel:{}", IdleStateChecker.class.getSimpleName(), readerTime, ctx.channel());
        ctx.channel().close();
    }

}
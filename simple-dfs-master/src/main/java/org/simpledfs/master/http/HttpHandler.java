package org.simpledfs.master.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.simpledfs.master.DefaultMasterPacketHandler;
import org.simpledfs.master.MasterContext;
import org.simpledfs.master.http.controller.HttpRequestHandler;
import org.simpledfs.master.http.controller.RequestHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpHandler extends SimpleChannelInboundHandler<Object> {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultMasterPacketHandler.class);

    private RequestHandlerFactory handlerFactory;

    private MasterContext context;

    public HttpHandler(MasterContext context) {
        this.context = context;
        this.handlerFactory = RequestHandlerFactory.getInstance();
    }


    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            HttpRequestHandler handler = handlerFactory.getHandler(request.getUri());

            HttpResponse response = handler.handle(request);
            writeResponse(ctx, response);
            LOGGER.info("frame={}", msg);
        }else{

        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
        LOGGER.error("ctx close,cause:", cause);
    }

    private void writeResponse(ChannelHandlerContext ctx, HttpResponse response) {
        if (response != null) {
            ctx.channel().writeAndFlush(response);
        }
    }

}

package org.simpledfs.core.packet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class FilePacketHandler extends SimpleChannelInboundHandler<Packet> {

    private final static Logger LOGGER = LoggerFactory.getLogger(FilePacketHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        byte type = packet.getType();
//        if (type == PacketType.PACKET_TYPE_REQUEST) {
//            onRequest(ctx, packet);
//        } else {
//            onResponse(packet);
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("Exception occurred cause={} will close the channel:{}", cause.getMessage(), ctx.channel(), cause);
        ctx.close();
    }

//    private void onRequest(ChannelHandlerContext ctx, Packet packet) {
//        // TODO how ServerSpeaker communicate with each other
//        // pre handle
//        Payload payload = InterceptorHandler.preHandle(ctx.channel(), packet);
//        if (!payload.isSuccess()) {
//            Packet response = PacketFactory.newResponsePacket(payload, packet.getId());
//            writeResponse(ctx, response);
//            return;
//        }
//        // if the packet should be handled async
//        if (packet.isHandleAsync()) {
//            EventExecutor channelExecutor = ctx.executor();
//            // create a promise
//            Promise<Packet> promise = new DefaultPromise<>(channelExecutor);
//            // async execute and get a future
//            Future<Packet> future = executor.asyncExecute(promise, ctx, packet);
//            future.addListener(new GenericFutureListener<Future<Packet>>() {
//                @Override
//                public void operationComplete(Future<Packet> f) throws Exception {
//                    if (f.isSuccess()) {
//                        Packet response = f.get();
//                        writeResponse(ctx, response);
//                    }
//                }
//            });
//        } else {
//            // sync execute and get the response packet
//            Packet response = executor.execute(ctx, packet);
//            writeResponse(ctx, response);
//        }
//    }
//
//    private void writeResponse(ChannelHandlerContext ctx, Packet response) {
//        if (response != null) {
//            ctx.channel().writeAndFlush(response);
//        }
//    }
//
//    private void onResponse(Packet packet) {
//        CompletableFuture<Packet> pending = PendingPackets.remove(packet.getId());
//        if (pending != null) {
//            // the response will be handled by client
//            // after the client future has been notified
//            // to be completed
//            pending.complete(packet);
//        }
//    }
}

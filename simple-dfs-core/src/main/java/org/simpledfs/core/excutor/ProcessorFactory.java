package org.simpledfs.core.excutor;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.req.Request;

public class ProcessorFactory {

    public Processor build(ChannelHandlerContext ctx, Packet packet){

        return null;
    }

    public Processor buildRequestProcessor(ChannelHandlerContext ctx, Packet packet){
        Request request = packet.getRequest();
        return null;
    }
}

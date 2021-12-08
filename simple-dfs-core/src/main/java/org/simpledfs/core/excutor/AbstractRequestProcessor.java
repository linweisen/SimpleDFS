package org.simpledfs.core.excutor;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.packet.Packet;

public abstract class AbstractRequestProcessor implements Processor {

    public void process(ChannelHandlerContext ctx, Packet packet){

    }

    public abstract void doProcess();


    @Override
    public void run() {

    }
}

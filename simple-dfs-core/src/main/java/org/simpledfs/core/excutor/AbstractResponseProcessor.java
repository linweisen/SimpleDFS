package org.simpledfs.core.excutor;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.packet.Packet;

public abstract class AbstractResponseProcessor implements Processor {

    public void process(ChannelHandlerContext ctx, Packet packet){

    }
}

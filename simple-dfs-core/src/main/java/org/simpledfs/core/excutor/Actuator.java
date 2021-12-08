package org.simpledfs.core.excutor;


import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.req.Request;

/**
 * @author houyi
 */
public interface Actuator<T> {


    void execute(ChannelHandlerContext ctx, Request request);


}

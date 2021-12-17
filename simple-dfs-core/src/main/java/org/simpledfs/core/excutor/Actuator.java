package org.simpledfs.core.excutor;


import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.req.Request;

/**
 * @author linweisen
 */
public interface Actuator<T> {

    void execute(ChannelHandlerContext ctx, Request request, Context context, Long packetId);


}

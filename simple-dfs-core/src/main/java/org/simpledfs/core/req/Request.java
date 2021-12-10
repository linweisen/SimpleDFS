package org.simpledfs.core.req;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.excutor.Processor;
import org.simpledfs.core.packet.Packet;

/**
 * <p>
 * A interface request
 * </p>
 *
 * <p>
 * The type of a Request is like blow:
 * +---------------+-------------------------------+----------+
 * |  hexadecimal  |  intro                        | response |
 * +---------------+-------------------------------+----------+
 * |    0x00       |  ping request                 |   0x80   |
 * +---------------+-------------------------------+----------+
 * |    0x01       |  create directory request     |   0x81   |
 * +---------------+-------------------------------+----------+
 * </p>
 *
 *
 *
 * @author linweisen
 */
public interface Request {

    byte getType();

    Processor buildSelfProcessor(ChannelHandlerContext ctx, Request request, Context context, Long packetId);
}

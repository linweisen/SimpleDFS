package org.simpledfs.core.req;

import io.netty.channel.ChannelHandlerContext;
import org.simpledfs.core.excutor.Processor;
import org.simpledfs.core.packet.Packet;

/**
 * <p>
 * A interface request
 * </p>
 *
 * <p>
 * The type of a Request is like blow:
 * +---------------+-------------------------------+
 * |  hexadecimal  |  intro                        |
 * +---------------+-------------------------------+
 * |    0x01       |  create directory request     |
 * +---------------+-------------------------------+
 * </p>
 *
 *
 * @author linweisen
 */
public interface Request {

    byte getType();

    Processor buildSelfProcessor(ChannelHandlerContext ctx, Request request);
}

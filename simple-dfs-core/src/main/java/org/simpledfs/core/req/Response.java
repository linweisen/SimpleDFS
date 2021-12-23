package org.simpledfs.core.req;

/**
 * <p>
 * A interface response
 * </p>
 *
 * <p>
 * The type of a Response is like blow:
 * +---------------+----------------------------+
 * |  hexadecimal  |  intro                     |
 * +---------------+----------------------------+
 * |    0x11       |  send file response        |
 * +---------------+----------------------------+
 * </p>
 *
 *
 * @author linweisen
 */
public interface Response {

    String getMessage();
}

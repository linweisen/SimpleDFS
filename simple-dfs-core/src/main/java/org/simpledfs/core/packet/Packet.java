package org.simpledfs.core.packet;

import org.simpledfs.core.req.Request;
import org.simpledfs.core.req.Response;

import java.io.Serializable;

/**
 * <p>
 * A base abstract packet
 * Each Detailed Packet should extends the {@link Packet}
 * </p>
 *
 * <p>
 * The structure of a Packet is like blow:
 * +----------+----------+----------------------------+
 * |  size    |  value   |  intro                     |
 * +----------+----------+----------------------------+
 * | 1 bytes  | 0xBC     |  magic number              |
 * | 1 bytes  |          |  type                      |
 * | 8 bytes  |          |  the type 1:req 2:res 3:cmd|
 * | 4 bytes  |          |  content length            |
 * | 1 bytes  |          |  serialize  algorithm      |
 * | 1 bytes  |          |  serialize  algorithm      |
 * +----------+----------+----------------------------+
 * </p>
 *
 *
 * @author linweisen
 */
public class Packet implements Serializable {

    public static byte MAGIC_CODE = (byte) 0xDF;

    private byte MAGIC = MAGIC_CODE;

    private byte type;

    private long id;

    private byte serialize;

    private int length;

    private Request request;

    private Response response;

    public byte getMAGIC() {
        return MAGIC;
    }

    public void setMAGIC(byte MAGIC) {
        this.MAGIC = MAGIC;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte getSerialize() {
        return serialize;
    }

    public void setSerialize(byte serialize) {
        this.serialize = serialize;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}

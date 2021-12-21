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
 * | 1 bytes  |  0xBC    |  magic number              |
 * | 1 bytes  |          |  type                      |
 * | 8 bytes  |          |  id                        |
 * | 1 bytes  |          |  serialize  algorithm      |
 * | 4 bytes  |          |  length                    |
 * | X bytes  |          |  body(request|response)    |
 * +----------+----------+----------------------------+
 * </p>
 *
 * <p>
 * The type of a Request and Response is like blow:
 * +---------------+-------------------------------+----------+
 * |  hexadecimal  |  intro                        | response |
 * +---------------+-------------------------------+----------+
 * |    0x00       |  ping request                 |   0x80   |
 * +---------------+-------------------------------+----------+
 * |    0x01       |  create directory request     |   0x81   |
 * +---------------+-------------------------------+----------+
 * |    0x02       |  save file request            |   0x82   |
 * +---------------+-------------------------------+----------+
 * </p>
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

    @Override
    public String toString() {
        return "Packet{" +
                "MAGIC=" + MAGIC +
                ", type=" + type +
                ", id=" + id +
                ", serialize=" + serialize +
                ", length=" + length +
                ", request=" + request +
                ", response=" + response +
                '}';
    }
}

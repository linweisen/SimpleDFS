package org.simpledfs.core.packet;

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

    private byte MAGIC = (byte) 0xDF;

    private byte type;

    private long id;

    private int length;

    private byte serialize;

    private byte[] content;
}

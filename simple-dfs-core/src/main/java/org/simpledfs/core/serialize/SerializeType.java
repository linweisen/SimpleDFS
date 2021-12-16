package org.simpledfs.core.serialize;

import java.util.Arrays;

/**
 * 序列化算法类型
 *
 * @author linweisen
 */
public enum SerializeType {

    Kryo((byte) 1);

    private byte type;

    SerializeType(byte type) {
        this.type = type;
    }

    public static SerializeType getEnum(Byte type) {
        return type == null ? null : Arrays.stream(values())
                .filter(t -> t.getType() == type)
                .findFirst()
                .orElse(null);
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}

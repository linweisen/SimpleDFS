package org.linweisen.common.protocol;

import org.linweisen.common.utils.KryoUtils;

public abstract class AbstractProtocol {

    private byte header;

    private int length;

    public AbstractProtocol(byte header) {
        this.header = header;
    }

    public byte getHeader() {
        return this.header;
    }

    public byte[] encode(AbstractProtocol protocol){
        return KryoUtils.writeToByteArray(protocol);
    }

}

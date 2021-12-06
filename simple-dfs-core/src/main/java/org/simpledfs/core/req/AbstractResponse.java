package org.simpledfs.core.req;

public abstract class AbstractResponse implements Response{

    private byte type;

    public AbstractResponse(byte type) {
        this.type = type;
    }
}

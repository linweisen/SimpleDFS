package org.simpledfs.core.req;

public abstract class AbstractRequest implements Request{

    private byte type;

    public AbstractRequest(byte type) {
        this.type = type;
    }
}

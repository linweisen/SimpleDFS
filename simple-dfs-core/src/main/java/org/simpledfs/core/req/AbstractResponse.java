package org.simpledfs.core.req;

public abstract class AbstractResponse implements Response{

    private byte type;

    private String message;

    private int code;

    public AbstractResponse(byte type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

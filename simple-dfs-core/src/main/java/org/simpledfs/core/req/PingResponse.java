package org.simpledfs.core.req;

public class PingResponse extends AbstractResponse {

    public PingResponse() {
        super((byte)0x80);
    }
}

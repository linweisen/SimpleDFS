package org.simpledfs.master.req;

import org.simpledfs.core.req.AbstractResponse;

public class PingResponse extends AbstractResponse {

    public PingResponse() {
        super((byte)0x80);
    }
}

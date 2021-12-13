package org.simpledfs.master.req;

import org.simpledfs.core.req.AbstractResponse;

public class MkdirResponse extends AbstractResponse {

    public MkdirResponse() {
        super((byte)0x81);
    }
}

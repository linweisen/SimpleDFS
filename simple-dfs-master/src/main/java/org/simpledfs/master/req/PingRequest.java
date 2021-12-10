package org.simpledfs.master.req;

import org.simpledfs.core.req.AbstractRequest;
import org.simpledfs.master.WorkInfo;

public class PingRequest extends AbstractRequest {

    public WorkInfo workInfo;

    public PingRequest() {
        super((byte)0x00);
    }
}

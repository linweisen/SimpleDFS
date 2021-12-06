package org.simpledfs.core.req;

public class ClientFileSendRequest extends AbstractRequest {

    private String fileName;

    private String id;



    public ClientFileSendRequest() {
        super((byte)0x01);
    }
}

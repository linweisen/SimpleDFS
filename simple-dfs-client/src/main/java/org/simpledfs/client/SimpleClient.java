package org.simpledfs.client;

import org.simpledfs.core.net.DefaultClient;
import org.simpledfs.core.net.ServerInfo;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.req.Request;
import org.simpledfs.core.req.Response;

/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/22
 **/
public class SimpleClient extends DefaultClient {

    private ClientInitializer initializer;

    public SimpleClient(ServerInfo serverInfo, boolean connectAsync) {
        super(serverInfo, connectAsync);
        this.initializer = new ClientInitializer();
        setInitializer(this.initializer);
    }

    public void connect(){
        if (isConnectAsync()){
            startAsync();
        }else{
            start();
        }
    }

    public Response send(Request request){
        Packet packet = new Packet();
        packet.setId(1L);
        packet.setType((byte)0x01);
        packet.setSerialize((byte)1);
        packet.setRequest(request);
        send(packet);
        return initializer.getResponse();
    }
}

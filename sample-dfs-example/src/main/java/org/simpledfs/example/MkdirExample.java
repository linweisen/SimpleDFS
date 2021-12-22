package org.simpledfs.example;

import org.simpledfs.client.SimpleClient;
import org.simpledfs.core.net.ServerInfo;
import org.simpledfs.core.packet.Packet;

/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/22
 **/
public class MkdirExample {

    public static void main(String[] args) {
        ServerInfo serverInfo = new ServerInfo("127.0.0.1", 8080);
        SimpleClient client = new SimpleClient(serverInfo, false);
        client.connect();
        Packet packet = new Packet();
        packet.setId(1L);
        packet.setType((byte)0x01);
        packet.setSerialize((byte)1);
//        MkdirRequest request = new MkdirRequest();
//        request.setName("/user");
//        request.setParent("/name");
//        packet.setRequest(request);
//
//        client.sendPacket(packet);
    }
}

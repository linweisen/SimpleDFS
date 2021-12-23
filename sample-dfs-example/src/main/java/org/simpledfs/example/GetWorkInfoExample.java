package org.simpledfs.example;

import org.simpledfs.client.SimpleClient;
import org.simpledfs.core.net.ServerInfo;
import org.simpledfs.core.req.MkdirRequest;
import org.simpledfs.core.req.Response;

/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/22
 **/
public class GetWorkInfoExample {

    public static void main(String[] args) {
        ServerInfo serverInfo = new ServerInfo("127.0.0.1", 8080);
        SimpleClient client = new SimpleClient(serverInfo, false);
        client.connect();
        MkdirRequest request = new MkdirRequest();
        request.setName("/name2");
        Response response = client.send(request);
    }
}

package org.simpledfs.client;

import org.simpledfs.core.net.DefaultClient;
import org.simpledfs.core.net.ServerInfo;

/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/22
 **/
public class SimpleClient extends DefaultClient {


    public SimpleClient(ServerInfo serverInfo, boolean connectAsync) {
        super(serverInfo, connectAsync);
    }
}

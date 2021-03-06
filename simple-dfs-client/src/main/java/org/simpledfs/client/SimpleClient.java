package org.simpledfs.client;

import org.simpledfs.core.net.DefaultClient;
import org.simpledfs.core.net.ServerInfo;

import java.io.File;

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

    public SimpleClient(ServerInfo serverInfo) {
        this(serverInfo, false);
    }

    public void connect(){
        if (isConnectAsync()){
            startAsync();
        }else{
            start();
        }
    }

    public void uploadFile(File file){

    }

    private void askForUploadFile(String path, String fileName){

    }
}

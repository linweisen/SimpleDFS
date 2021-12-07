package org.simpledfs.master;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpledfs.core.net.Server;

public class Master {

    private static Logger LOGGER = LogManager.getLogger(Master.class);

    private Server server;

    public void start(int port){
        init();

    }

    public void init(){
        LOGGER.info("start init...");
    }

    public static void main(String[] args) {
        Master workServer = new Master();
        workServer.start(8080);
    }
}

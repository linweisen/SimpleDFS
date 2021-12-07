package org.simpledfs.master;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpledfs.core.config.Configuration;
import org.simpledfs.core.config.ConfigurationParser;
import org.simpledfs.core.net.DefaultServer;
import org.simpledfs.core.net.Server;

public class Master {

    private static Logger LOGGER = LogManager.getLogger(Master.class);

    private Server server;

    private Configuration config;

    public Master(String masterConfigFile) {
        this.config = new Configuration(ConfigurationParser.read(masterConfigFile));
        MasterServerInitializer initializer = new MasterServerInitializer();
        int port = this.config.getInt("master.port", 8080);
        this.server = new DefaultServer(Master.class, initializer, port);
    }

    public void start(int port){
        init();
    }

    public void init(){
        LOGGER.info("start init...");
    }

    public static void main(String[] args) {
//        Master workServer = new Master();
//        workServer.start(8080);
    }
}

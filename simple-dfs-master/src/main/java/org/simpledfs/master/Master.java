package org.simpledfs.master;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpledfs.core.command.Command;
import org.simpledfs.core.config.Configuration;
import org.simpledfs.core.config.ConfigurationParser;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.dir.Directory;
import org.simpledfs.core.dir.DirectoryLock;
import org.simpledfs.core.dir.IDirectory;
import org.simpledfs.core.net.DefaultServer;
import org.simpledfs.core.net.Server;

import java.util.Properties;

public class Master {

    private static Logger LOGGER = LogManager.getLogger(Master.class);

    private Server server;

    private Configuration config;

    private IDirectory root;

    private MasterContext context;

    public Master(Command command) {
        String masterConfigFile = command.getFile();
        Properties properties = ConfigurationParser.read(masterConfigFile);
        if (properties == null){
            LOGGER.error("configuration parse filed");
            System.exit(0);
        }
        this.config = new Configuration(properties);
        init();
    }

    public void start(){
        this.server.start();
    }

    public void init(){
        LOGGER.info("start init...");
        shutdownHook();
        //init root directory
        root = new Directory();
        root.setName("/");
        DirectoryLock.getInstance().addLock(root.getName());
        this.context = new MasterContext(root);
        //init network connect
        MasterServerInitializer initializer = new MasterServerInitializer(context);
        int port = this.config.getInt("master.port", 8080);
        this.server = new DefaultServer(Master.class, initializer, port);

    }

    public void shutdownHook(){
        Thread t = new Thread(new Runnable() {
            public void run() {
                LOGGER.info("release...");
            }
        }, "shutdownHook-thread");
        t.setDaemon(true);
        Runtime.getRuntime().addShutdownHook(t);
    }

    public static void main(String[] args) {
        Command command = new Command();
        JCommander jct = JCommander.newBuilder()
                .addObject(command)
                .build();
        jct.setProgramName("SimpleDfs Master");
        try {
            jct.parse(args);
            if (command.isHelp()) {
                jct.usage();
                return;
            }
        }
        catch (ParameterException e){
            e.printStackTrace();
            jct.usage();
            System.exit(0);
        }
        Master master = new Master(command);
        master.start();

    }
}

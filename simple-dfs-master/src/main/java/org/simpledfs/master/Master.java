package org.simpledfs.master;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpledfs.core.command.Command;
import org.simpledfs.core.config.Configuration;
import org.simpledfs.core.config.ConfigurationParser;
import org.simpledfs.core.context.MetaContext;
import org.simpledfs.core.dir.Directory;
import org.simpledfs.core.dir.DirectoryLock;
import org.simpledfs.core.dir.IDirectory;
import org.simpledfs.core.net.DefaultServer;
import org.simpledfs.core.net.Server;

import java.util.Properties;

/**
 * Master节点
 *
 * @author linweisen
 * @date 2021/12/14
 * @version 1.0
 **/
public class Master {

    private static Logger LOGGER = LogManager.getLogger(Master.class);

    private Server server;

    private MetaContext meta;

    public Master(Command command) {
        String masterConfigFile = command.getFile();
        Properties properties = ConfigurationParser.read(masterConfigFile);
        if (properties == null){
            LOGGER.error("configuration parse filed");
            System.exit(0);
        }
        if (checkProperties(properties)){
            Configuration config = new Configuration(properties);
            init(config);
        }else{
            System.exit(0);
        }
    }

    public void start(){
        this.server.start();
    }

    public void init(Configuration config){
        LOGGER.info("start init...");
        shutdownHook();

        //init root directory
        IDirectory root = new Directory("/");
        DirectoryLock.getInstance().addLock(root.getName());

        //build master context
        this.meta = new MetaContext.BuildContext()
                                        .root(root)
                                        .config(config)
                                        .build();

        //init network connect
        MasterServerInitializer initializer = new MasterServerInitializer(this.meta);
        int port = config.getInt("master.port", 8080);
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

    private boolean checkProperties(Properties properties){
        if (!properties.containsKey(MasterConfigurationKey.MASTER_PORT)){
            LOGGER.error("master configuration must contains property<master.port>...");
            return false;
        }
        if (!properties.contains(MasterConfigurationKey.IDLE_TIME)){
            properties.setProperty(MasterConfigurationKey.IDLE_TIME, "60000");
        }
        return true;
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

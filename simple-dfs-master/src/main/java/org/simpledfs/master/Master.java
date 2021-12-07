package org.simpledfs.master;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpledfs.core.command.Command;
import org.simpledfs.core.config.Configuration;
import org.simpledfs.core.config.ConfigurationParser;
import org.simpledfs.core.net.DefaultServer;
import org.simpledfs.core.net.Server;

import java.util.Properties;

public class Master {

    private static Logger LOGGER = LogManager.getLogger(Master.class);

    private Server server;

    private Configuration config;

    public Master(String masterConfigFile) {
        Properties properties = ConfigurationParser.read(masterConfigFile);
        if (properties == null){
            LOGGER.error("configuration parse filed");
            System.exit(0);
        }
        this.config = new Configuration(ConfigurationParser.read(masterConfigFile));
        MasterServerInitializer initializer = new MasterServerInitializer();
        int port = this.config.getInt("master.port", 8080);
        this.server = new DefaultServer(Master.class, initializer, port);
    }

    public void start(){
        init();
    }

    public void init(){
        LOGGER.info("start init...");
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
        String file = "src/main/resources/generator.xml";
        Master workServer = new Master(file);
    }
}

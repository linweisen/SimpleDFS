package org.simpledfs.work;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpledfs.core.command.Command;
import org.simpledfs.core.config.Configuration;
import org.simpledfs.core.config.ConfigurationParser;
import org.simpledfs.core.net.*;
import org.simpledfs.core.utils.LocalAddressUtils;
import org.simpledfs.core.uuid.SnowflakeGenerator;
import org.simpledfs.core.uuid.UUIDGenerator;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.UUID;

/**
 * work节点
 *
 * @author linweisen
 * @date 2021/12/14
 * @version 1.0
 **/
public class Work {

    private static Logger LOGGER = LogManager.getLogger(Work.class);

    private Client client;

    private Server server;

    private Configuration config;

    private NodeInfoSelfUpdater updater;

    private UUIDGenerator uuidGenerator;

    public Work(Command command) {
        String masterConfigFile = command.getFile();
        Properties properties = ConfigurationParser.read(masterConfigFile);
        if (properties == null){
            LOGGER.error("configuration of the work node parse filed, work will exit...");
            System.exit(0);
        }
        if (checkProperties(properties)){
            this.config = new Configuration(properties);
            init();
        }

    }

    public void start() {
        this.updater.start();
        this.client.startAsync();
        Object clientMonitor = ((DefaultClient)this.client).getStartMonitor();
        try {
            synchronized (clientMonitor){
                clientMonitor.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.server.start();
    }

    public void init(){
        LOGGER.info("{} work node start init...", config.getString(""));
        shutdownHook();

        //todo node and datacenter need configuration?
        uuidGenerator = new SnowflakeGenerator(0, 0);
        //init work info updater
        this.updater = new NodeInfoSelfUpdater(config.getString(WorkConfigurationKey.WORK_ID),
                config.getString(WorkConfigurationKey.WORK_NAME), 6000);

        //init master link client
        String masterInfo = this.config.getString(WorkConfigurationKey.MASTER_ADDRESS);
        String[] masterInfoArray = masterInfo.split(":");
        ServerInfo serverInfo = new ServerInfo(masterInfoArray[0], Integer.valueOf(masterInfoArray[1]));
        this.client = new DefaultClient(serverInfo, true);
        WorkClientInitializer clientInitializer = new WorkClientInitializer(this.updater.getNodeInfo(), this.client, this.config);
        this.client.setInitializer(clientInitializer);

        //init work server...
        int port = this.config.getInt(WorkConfigurationKey.WORK_PORT, 8080);
        this.server = new DefaultServer(Work.class, new WorkServerInitializer(), port);
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
        if (!properties.containsKey(WorkConfigurationKey.MASTER_ADDRESS)){
            LOGGER.error("work configuration must contains property<master.address>...");
            return false;
        }

        if (!properties.containsKey(WorkConfigurationKey.WORK_ID)){
            try {
                properties.setProperty(WorkConfigurationKey.WORK_ID, String.valueOf(LocalAddressUtils.getLocalIPIndex()));
            } catch (UnknownHostException | SocketException e) {
                LOGGER.error("Get local ip failed");
                e.printStackTrace();
                properties.setProperty(WorkConfigurationKey.WORK_ID, UUID.randomUUID().toString());
            }
        }
        if (!properties.containsKey(WorkConfigurationKey.WORK_NAME)){
            properties.setProperty(WorkConfigurationKey.WORK_NAME, "work-" + properties.getProperty(WorkConfigurationKey.WORK_ID));
        }

        return true;
    }
    

    public static void main(String[] args) {
        Command command = new Command();
        JCommander jct = JCommander.newBuilder()
                .addObject(command)
                .build();
        jct.setProgramName("SimpleDfs Work");
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
        Work work = new Work(command);
        work.start();
    }
}

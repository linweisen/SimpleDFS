package org.simpledfs.work;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpledfs.core.command.Command;
import org.simpledfs.core.config.Configuration;
import org.simpledfs.core.config.ConfigurationParser;
import org.simpledfs.core.net.*;
import org.simpledfs.master.WorkInfo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class Work {

    private static Logger LOGGER = LogManager.getLogger(Work.class);

    private Client client;

    private Server server;

    private Configuration config;

    public Work(Command command) {
        String masterConfigFile = command.getFile();
        Properties properties = ConfigurationParser.read(masterConfigFile);
        if (properties == null){
            LOGGER.error("configuration parse filed");
            System.exit(0);
        }
        this.config = new Configuration(properties);
        init();
    }

    public void start() {
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
        LOGGER.info("start init...");
        shutdownHook();


        //init master link client
        String masterInfo = this.config.getString("master.address");
        if (masterInfo == null){
            LOGGER.error("");
            System.exit(0);
        }
        String[] masterInfoArray = masterInfo.split(":");
        ServerInfo serverInfo = new ServerInfo(masterInfoArray[0], Integer.valueOf(masterInfoArray[1]));
        this.client = new DefaultClient(serverInfo, true);
        WorkClientInitializer clientInitializer = new WorkClientInitializer(this.client, this.config);
        this.client.setInitializer(clientInitializer);

        //init work server...
        int port = this.config.getInt("master.port", 8080);
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

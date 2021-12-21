package org.simpledfs.master;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpledfs.core.command.Command;
import org.simpledfs.core.config.Configuration;
import org.simpledfs.core.config.ConfigurationParser;
import org.simpledfs.core.context.MetaContext;
import org.simpledfs.core.dir.*;
import org.simpledfs.core.net.DefaultServer;
import org.simpledfs.core.net.Server;
import org.simpledfs.core.utils.MD5Utils;

import java.io.FileNotFoundException;
import java.util.List;
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

    private Snapshot snapshot;

    public Master(Command command) {
        String masterConfigFile = command.getFile();
        Properties properties = ConfigurationParser.read(masterConfigFile);
        if (properties == null){
            LOGGER.error("master configuration parse failed...");
            System.exit(0);
        }
        if (checkProperties(properties)){
            Configuration config = new Configuration(properties);
            init(config);
        }else{
            LOGGER.error("configuration check failed, master node will exit...");
            System.exit(0);
        }
    }


    public void start(){
        this.server.start();
    }

    /**
     * 初始化master节点:
     * 1、构建目录树
     * 2、构建元数据context
     * 3、构建netty server
     *
     * @Author: linweisen
     * @Date: 2021/12/16
     * @Version: 1.0
    **/
    public void init(Configuration config){
        LOGGER.info("master node start init...");
        shutdownHook();

        /*
         * 判断镜像文件是否为空,之后为每个root下的子目录生成读写锁
         * 1、为空则初始化root directory
         * 2、不为空则从镜像文件中初始化整个directory tree
         */
        try {
            this.snapshot = new DefaultSnapshot(config.getString(MasterConfigurationKey.STORAGE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.error("{} must been created before master init, master will exit...", config.getString(MasterConfigurationKey.STORAGE_PATH));
            System.exit(0);
        }

        IDirectory root = null;
        if (snapshot.isEmptySnapshot()){
            root = new Directory(IDirectory.SEPARATOR, IDirectory.ROOT_PARENT);
            root.setId(MD5Utils.getMD5String(IDirectory.SEPARATOR));
            root.setINode(INode.build("admin", "admin", new char[]{'-','d','r','x','r','w','x','r','w','x'}));
            snapshot.write(root);
        }else{
            root = snapshot.read();
            List<IDirectory> children = root.getChildren();
            if (children != null){
                for (IDirectory d : children){
                    DirectoryLock.getInstance().addLock(d.getName());
                }
            }
        }
        DirectoryLock.getInstance().addLock(root.getName());

        this.meta = new MetaContext.BuildContext()
                                        .root(root)
                                        .config(config)
                                        .snapshot(snapshot)
                                        .build();

        MasterServerInitializer initializer = new MasterServerInitializer(this.meta);
        int port = config.getInt(MasterConfigurationKey.MASTER_PORT, 8080);
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

    /**
     * 检查必要的master配置参数
     *
     * @Author: linweisen
     * @Date: 2021/12/16
     * @Version: 1.0
    **/
    private boolean checkProperties(Properties properties){
        if (!properties.containsKey(MasterConfigurationKey.MASTER_PORT)){
            LOGGER.error("master configuration must contains property<master.port>...");
            return false;
        }
        if (!properties.containsKey(MasterConfigurationKey.IDLE_TIME)){
            properties.setProperty(MasterConfigurationKey.IDLE_TIME, "60000");
        }
        if (!properties.containsKey(MasterConfigurationKey.STORAGE_PATH)){
            LOGGER.error("master configuration must contains property<storage.path>...");
            return false;
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

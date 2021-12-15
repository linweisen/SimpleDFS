package org.simpledfs.work;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpledfs.core.node.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * 本地节点更新器，持有一个线程，不断获取本地cpu，内存以及磁盘信息
 *
 * @author linweisen
 * @date 2021/12/14
 * @version 1.0
**/
public class NodeInfoSelfUpdater {

    private static Logger LOGGER = LogManager.getLogger(NodeInfoSelfUpdater.class);

    private volatile NodeInfo nodeInfo;

    private Thread t;

    public NodeInfoSelfUpdater(String id, String name, int interval) {
        nodeInfo = new NodeInfo();
        List<SystemInfo> systemInfoList = new ArrayList<>();
        systemInfoList.add(new MemInfo());
        systemInfoList.add(new CpusInfo());
        systemInfoList.add(new DiskInfo());
        nodeInfo.setSystemInfoList(systemInfoList);
        nodeInfo.setName(name);
        nodeInfo.setId(id);
        try {
            nodeInfo.setAddress(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            nodeInfo.setAddress("127.0.0.1");
            LOGGER.error("{} get localhost wrong...", name);
            e.printStackTrace();
        }
        t = new Thread(new Updater(nodeInfo, interval));
        t.setDaemon(true);
        t.setName("node-updater-thread");
    }

    public void start(){
        t.start();
    }

    public void stop(){
        t.interrupt();
    }

    public NodeInfo getNodeInfo() {
        return nodeInfo;
    }

    private static class Updater implements Runnable {

        private NodeInfo nodeInfo;

        private int interval;

        public Updater(NodeInfo nodeInfo, int interval) {
            this.nodeInfo = nodeInfo;
            this.interval = interval;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                nodeInfo.getSystemInfoList().forEach(l->{
                    l.obtain();
                });
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
            LOGGER.info("{} detect interrupt flag, thread exit....", NodeInfoSelfUpdater.class.getSimpleName());

        }
    }
}

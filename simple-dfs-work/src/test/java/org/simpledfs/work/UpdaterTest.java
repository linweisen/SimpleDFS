package org.simpledfs.work;

import org.junit.Test;

public class UpdaterTest {

    @Test
    public void test() throws InterruptedException {

        NodeInfoSelfUpdater updater = new NodeInfoSelfUpdater("node1", 2000);
        updater.start();

        int counter = 0;
        while (counter < 10){
            Thread.sleep(5000);
            updater.getNodeInfo().getSystemInfoList().forEach(System.out::println);
            counter++;
        }
        updater.stop();

        Thread.sleep(50000);

    }
}

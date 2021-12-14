package com.simpledfs.core;

import org.junit.Test;
import org.simpledfs.core.node.DiskInfo;
import org.simpledfs.core.node.MemInfo;

public class SystemInfoGetTest {

    @Test
    public void memTest(){
        MemInfo memInfo = new MemInfo();
        memInfo.obtain();
        System.out.println(memInfo);
    }

    @Test
    public void diskTest(){
        DiskInfo diskInfo = new DiskInfo();
        diskInfo.obtain();
        System.out.println(diskInfo);
    }
}

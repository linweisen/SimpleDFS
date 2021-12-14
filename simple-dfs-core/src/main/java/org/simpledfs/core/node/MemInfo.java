package org.simpledfs.core.node;

import com.sun.management.OperatingSystemMXBean;

import java.io.Serializable;
import java.lang.management.ManagementFactory;

public class MemInfo implements SystemInfo, Serializable {

    private long systemMemSize;

    private long systemUsedMemSize;

    private long systemIdleMemSize;

    private long heapMemSize;

    private long heapUsedMeMSize;

    private long heapIdleMemSize;

    public long getSystemMemSize() {
        return systemMemSize;
    }

    public void setSystemMemSize(long systemMemSize) {
        this.systemMemSize = systemMemSize;
    }

    public long getSystemUsedMemSize() {
        return systemUsedMemSize;
    }

    public void setSystemUsedMemSize(long systemUsedMemSize) {
        this.systemUsedMemSize = systemUsedMemSize;
    }

    public long getSystemIdleMemSize() {
        return systemIdleMemSize;
    }

    public void setSystemIdleMemSize(long systemIdleMemSize) {
        this.systemIdleMemSize = systemIdleMemSize;
    }

    public long getHeapMemSize() {
        return heapMemSize;
    }

    public void setHeapMemSize(long heapMemSize) {
        this.heapMemSize = heapMemSize;
    }

    public long getHeapUsedMeMSize() {
        return heapUsedMeMSize;
    }

    public void setHeapUsedMeMSize(long heapUsedMeMSize) {
        this.heapUsedMeMSize = heapUsedMeMSize;
    }

    public long getHeapIdleMemSize() {
        return heapIdleMemSize;
    }

    public void setHeapIdleMemSize(long heapIdleMemSize) {
        this.heapIdleMemSize = heapIdleMemSize;
    }

    @Override
    public void obtain() {
        Runtime rt = Runtime.getRuntime();
        this.heapMemSize = rt.maxMemory();
        this.heapUsedMeMSize = rt.totalMemory();
        this.heapIdleMemSize = rt.freeMemory();

        OperatingSystemMXBean osMxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        this.systemMemSize = osMxb.getTotalPhysicalMemorySize();
        this.systemIdleMemSize = osMxb.getFreePhysicalMemorySize();
        this.systemUsedMemSize = this.systemMemSize - this.systemIdleMemSize;
    }

    @Override
    public String toString() {
        int Mb = 1024 * 1024;
        return "MemInfo{" +
                "systemMemSize=" + systemMemSize / Mb + "MB" +
                ", systemUsedMemSize=" + systemUsedMemSize / Mb + "MB" +
                ", systemIdleMemSize=" + systemIdleMemSize / Mb + "MB" +
                ", heapMemSize=" + heapMemSize / Mb + "MB" +
                ", heapUsedMeMSize=" + heapUsedMeMSize / Mb + "MB" +
                ", heapIdleMemSize=" + heapIdleMemSize / Mb + "MB" +
                '}';
    }
}

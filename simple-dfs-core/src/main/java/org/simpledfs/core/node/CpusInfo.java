package org.simpledfs.core.node;

public class CpusInfo implements SystemInfo {

    private int cpus;

    @Override
    public void obtain() {
        this.cpus = Runtime.getRuntime().availableProcessors();
    }

    @Override
    public String toString() {
        return "CpusInfo{" +
                "cpus=" + cpus +
                '}';
    }
}

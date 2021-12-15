package org.simpledfs.core.node;

import java.io.Serializable;

public class CpusInfo implements SystemInfo, Serializable {

    private int cpus;

    public int getCpus() {
        return cpus;
    }

    public void setCpus(int cpus) {
        this.cpus = cpus;
    }

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

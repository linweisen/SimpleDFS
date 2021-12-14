package org.simpledfs.master;

public class WorkInfo {

    private String id;

    private String name;

    private String address;

    private int port;

    private long lastAcceptTime;

    private int cpus;

    private long men;

    private long disk;

    private long storageFileSize;

    public int getCpus() {
        return cpus;
    }

    public void setCpus(int cpus) {
        this.cpus = cpus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public long getLastAcceptTime() {
        return lastAcceptTime;
    }

    public void setLastAcceptTime(long lastAcceptTime) {
        this.lastAcceptTime = lastAcceptTime;
    }

    public long getMen() {
        return men;
    }

    public void setMen(long men) {
        this.men = men;
    }

    public long getDisk() {
        return disk;
    }

    public void setDisk(long disk) {
        this.disk = disk;
    }

    public long getStorageFileSize() {
        return storageFileSize;
    }

    public void setStorageFileSize(long storageFileSize) {
        this.storageFileSize = storageFileSize;
    }
}

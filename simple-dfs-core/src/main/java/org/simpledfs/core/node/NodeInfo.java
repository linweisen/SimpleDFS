package org.simpledfs.core.node;

import java.util.List;

public class NodeInfo {

    private String id;

    private String name;

    private String address;

    private List<SystemInfo> systemInfoList;

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

    public List<SystemInfo> getSystemInfoList() {
        return systemInfoList;
    }

    public void setSystemInfoList(List<SystemInfo> systemInfoList) {
        this.systemInfoList = systemInfoList;
    }
}

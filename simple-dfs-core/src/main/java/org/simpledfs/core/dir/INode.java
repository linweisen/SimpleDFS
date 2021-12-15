package org.simpledfs.core.dir;

public class INode {

    private long size;

    private String user;

    private String group;

    private char[] permission;

    private long updateTime;

    public INode() {
        permission = new char[]{'-','-','-','-','-','-','-','-','-','-'};
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public char[] getPermission() {
        return permission;
    }

    public void setPermission(char[] permission) {
        this.permission = permission;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}

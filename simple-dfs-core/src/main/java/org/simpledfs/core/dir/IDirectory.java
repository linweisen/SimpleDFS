package org.simpledfs.core.dir;

import java.util.List;

public abstract class IDirectory {

    public static String SEPARATOR = "/";

    private String name;

    private INode iNode;

    public abstract boolean isDirectory();

    public abstract List<IDirectory> getChildren();

    public abstract IDirectory findDirectory(String name);

    public IDirectory createChildDir(String name){
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public INode getINode() {
        return iNode;
    }

    public void setINode(INode iNode) {
        this.iNode = iNode;
    }
}

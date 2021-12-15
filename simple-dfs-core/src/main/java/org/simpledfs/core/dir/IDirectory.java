package org.simpledfs.core.dir;

import org.simpledfs.core.serialize.Serializer;

import java.io.Serializable;
import java.util.List;

public abstract class IDirectory implements Serializable {

    private SnapshotHeader header;

    public static String SEPARATOR = "/";

    private String name;

    private INode iNode;

    public IDirectory() {
    }

    public IDirectory(String name) {
        this.name = name;
    }

    public SnapshotHeader getHeader() {
        return header;
    }

    public void setHeader(SnapshotHeader header) {
        this.header = header;
    }

    public abstract boolean isDirectory();

    public byte[] serialize(Serializer serializer){
        return serializer.serialize(this);
    }

    public List<IDirectory> getChildren(){
        return null;
    }

    public abstract void addChildDirectory(IDirectory directory);

    public IDirectory findDirectory(String name){
        return null;
    }

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

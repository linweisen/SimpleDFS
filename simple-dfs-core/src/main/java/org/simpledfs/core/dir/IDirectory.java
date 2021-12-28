package org.simpledfs.core.dir;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.simpledfs.core.serialize.Serializer;

import java.io.Serializable;
import java.util.List;

public abstract class IDirectory implements Serializable {

    @JsonIgnore
    private SnapshotHeader header;

    public static String SEPARATOR = "/";

    public static String ROOT_PARENT = "ROOTS";

    @JsonIgnore
    private String id;

    @JsonIgnore
    private String parentId;

    /**
     * 节点名称
     */
    private String name;

    /**
     * inode 权限属性
     */
    private INode iNode;

    public IDirectory() {
    }

    public IDirectory(String name, String parentId) {
        this.name = name;
        this.parentId = parentId;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}

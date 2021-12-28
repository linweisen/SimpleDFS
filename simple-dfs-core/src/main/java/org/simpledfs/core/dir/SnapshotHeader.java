package org.simpledfs.core.dir;

/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/15
 **/
public class SnapshotHeader {

    /**
     * 索引位置
     */
    private long index;

    /**
     * 1 是删除
     */
    private byte isDeleted = 0;

    /**
     * 0 is directory
     * 1 is file
     */
    private byte isDirectory;

    private int size;

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public byte getIsDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(byte isDirectory) {
        this.isDirectory = isDirectory;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

package org.linweisen.common.protocol;

public class FileTransferProtocol extends AbstractProtocol{

    private String fileName;

    private int segment;

    private int index;

    private String id;

    private byte[] content;

    public FileTransferProtocol() {
        super((byte)0x01);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getSegment() {
        return segment;
    }

    public void setSegment(int segment) {
        this.segment = segment;
    }

}

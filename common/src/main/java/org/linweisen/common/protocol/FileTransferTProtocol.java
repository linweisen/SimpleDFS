package org.linweisen.common.protocol;

public class FileTransferTProtocol extends AbstractProtocol{

    private byte header = 0x01;

    private String fileName;

    private int segment;

    private int index;

    private String id;

    private byte[] content;

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

    @Override
    public byte getType() {
        return this.header;
    }
}

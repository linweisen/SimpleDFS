package org.simpledfs.core.dir;

import org.simpledfs.core.serialize.KryoSerializer;
import org.simpledfs.core.serialize.Serializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/15
 **/
public class DefaultSnapshot implements Snapshot {

    private String directoryFileName = "meta";

    private String fileBlockFileName = "block";

    private RandomAccessFile directoryFile;

    private RandomAccessFile fileBlockFile;

    private String storagePath;

    private Serializer serializer;

    public DefaultSnapshot(String storagePath) {
        this(storagePath, KryoSerializer.getInstance());
    }

    public DefaultSnapshot(String storagePath, Serializer serializer) {
        this.storagePath = storagePath;
        try {
            if (!storagePath.endsWith(IDirectory.SEPARATOR)){
                storagePath += IDirectory.SEPARATOR;
            }
            this.directoryFile = new RandomAccessFile(new File(storagePath + directoryFileName), "rw");
            this.fileBlockFile = new RandomAccessFile(new File(storagePath + fileBlockFileName), "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.serializer = serializer;
    }

    @Override
    public void write(IDirectory directory) {
        SnapshotHeader header = directory.getHeader();
        if (header != null){
            long index = header.getIndex();
            try {
                directoryFile.seek(index + 4);
                directoryFile.writeByte(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte[] b = directory.serialize(serializer);
        header = new SnapshotHeader();
        header.setSize(b.length);
        header.setIsDirectory((byte)(directory.isDirectory() ? 0 : 1));
        try {
            long last = directoryFile.length();
            directoryFile.seek(last);
            header.setIndex(last);
            directoryFile.writeLong(header.getIndex());
            directoryFile.writeByte(header.getIsDeleted());
            directoryFile.writeByte(header.getIsDeleted());
            directoryFile.writeInt(header.getSize());
            directory.setHeader(header);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public IDirectory read() {
        try {
            long index = directoryFile.readLong();
            byte isDirectory = directoryFile.readByte();
            byte isDeleted = directoryFile.readByte();
            int size = directoryFile.readInt();
            byte[] b = new byte[size];
            int i = directoryFile.read(b);
            if (i != -1){
                IDirectory root = serializer.deserialize(b, IDirectory.class);
                System.out.println(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

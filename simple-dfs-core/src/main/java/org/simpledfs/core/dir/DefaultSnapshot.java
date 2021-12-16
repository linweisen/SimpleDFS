package org.simpledfs.core.dir;

import org.simpledfs.core.serialize.KryoSerializer;
import org.simpledfs.core.serialize.Serializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

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
        header.setIsDirectory((byte)(directory.isDirectory() ? 1 : 0));
        try {
            long last = directoryFile.length();
            directoryFile.seek(last);
            header.setIndex(last);
            directoryFile.writeLong(header.getIndex());
            directoryFile.writeByte(header.getIsDirectory());
            directoryFile.writeByte(header.getIsDeleted());
            directoryFile.writeInt(header.getSize());
            directoryFile.write(b);
            directory.setHeader(header);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public IDirectory read() {
        IDirectory root = null;
        Map<String, IDirectory> cache = new HashMap<>();
        long index;
        byte isDirectory;
        byte isDeleted;
        int size;
        try {
            index = directoryFile.readLong();
            while (index < directoryFile.length()){
                isDirectory = directoryFile.readByte();
                isDeleted = directoryFile.readByte();
                size = directoryFile.readInt();
                byte[] b = new byte[size];
                int i = directoryFile.read(b);
                IDirectory d = serializer.deserialize(b, IDirectory.class);
                SnapshotHeader header = new SnapshotHeader();
                header.setIndex(index);
                header.setSize(size);
                header.setIsDeleted(isDeleted);
                header.setIsDirectory(isDirectory);
                d.setHeader(header);
                cache.put(d.getId(), d);
                if ((directoryFile.getFilePointer() + 64) < directoryFile.length()){
                    index = directoryFile.readLong();
                }else{
                    break;
                }
            }
            for (Map.Entry<String, IDirectory> entry : cache.entrySet()){
                IDirectory d = entry.getValue();
                if (!d.getParentId().equals("-1")){
                    IDirectory p = cache.get(d.getParentId());
                    p.addChildDirectory(d);
                }else {
                    root = d;
                }
            }
            cache.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    @Override
    public boolean isEmptySnapshot() {
        try {
            if (directoryFile.length() == 0){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

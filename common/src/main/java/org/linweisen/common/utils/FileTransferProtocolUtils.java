package org.linweisen.common.utils;

import org.linweisen.common.protocol.FileTransferProtocol;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class FileTransferProtocolUtils {

    public static List<FileTransferProtocol> build(String fileName, int blockSize, String id) throws IOException {
        return build(new File(fileName), blockSize, id);
    }

    public static List<FileTransferProtocol> build(File file, int blockSize, String id) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        long len = file.length();
        long block = (long)Math.ceil( len * 1.0 / blockSize);
        List<FileTransferProtocol> fpList = new ArrayList<>();
        for(int i = 0; i < block; i++) {
            int tmp = blockSize;
            if (i == block - 1){
                tmp = (int)(len - i * blockSize);
            }
            raf.seek(i * (long)blockSize);
            byte[] size = new byte[tmp];
            int read = raf.read(size);
            if (read != -1){
                FileTransferProtocol fp = new FileTransferProtocol();
                fp.setFileName(file.getName());
                fp.setSegment((int)block);
                fp.setId(id);
                fp.setIndex(i);
                fp.setContent(size);
                fpList.add(fp);
            }
        }
        return fpList;
    }

}

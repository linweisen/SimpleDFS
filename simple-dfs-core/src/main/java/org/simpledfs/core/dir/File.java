package org.simpledfs.core.dir;

import org.simpledfs.core.serialize.Serializer;

import java.util.List;

public class File extends IDirectory {

    private List<FileBlock> fileBlockList;

    public File() {
    }

    public File(String name) {
        super(name);
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public void addChildDirectory(IDirectory directory) {

    }


}

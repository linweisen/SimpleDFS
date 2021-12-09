package org.simpledfs.core.dir;

import java.util.List;

public class File extends IDirectory {

    private List<FileBlock> fileBlockList;

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public List<IDirectory> getChildren() {
        return null;
    }

    @Override
    public IDirectory findDirectory(String name) {
        return null;
    }
}

package org.simpledfs.core.dir;

import java.util.List;

public class Directory extends IDirectory {

    private List<IDirectory> childrenList;

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public List<IDirectory> getChildren() {
        return childrenList;
    }
}

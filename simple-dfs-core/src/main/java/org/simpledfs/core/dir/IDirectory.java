package org.simpledfs.core.dir;

import java.util.List;

public abstract class IDirectory {

    private String name;

    private INode iNode;

    public abstract boolean isDirectory();

    public abstract List<IDirectory> getChildren();

}

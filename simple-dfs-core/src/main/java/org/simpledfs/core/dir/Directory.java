package org.simpledfs.core.dir;

import java.util.ArrayList;
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

    @Override
    public IDirectory findDirectory(String name) {
        for (IDirectory directory : childrenList){
            if (directory.getName().equals(name)){
                return directory;
            }
        }
        return null;
    }

    @Override
    public IDirectory createChildDir(String name) {
        if (childrenList == null){
            childrenList = new ArrayList<>();
        }
        IDirectory directory = new Directory();
        directory.setName(name);
        childrenList.add(directory);
        return directory;
    }
}

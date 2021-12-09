package org.simpledfs.master;

import org.simpledfs.core.context.Context;
import org.simpledfs.core.dir.IDirectory;

public class MasterContext implements Context {

    private IDirectory root;

    public MasterContext(IDirectory root) {
        this.root = root;
    }

    public IDirectory getRoot() {
        return root;
    }

    public void setRoot(IDirectory root) {
        this.root = root;
    }
}

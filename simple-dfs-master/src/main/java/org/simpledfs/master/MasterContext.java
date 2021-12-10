package org.simpledfs.master;

import org.simpledfs.core.config.Configuration;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.dir.IDirectory;

public class MasterContext implements Context {

    private Configuration config;

    private IDirectory root;

    public MasterContext(IDirectory root, Configuration config) {
        this.root = root;
        this.config = config;
    }

    public IDirectory getRoot() {
        return root;
    }

    public void setRoot(IDirectory root) {
        this.root = root;
    }
}

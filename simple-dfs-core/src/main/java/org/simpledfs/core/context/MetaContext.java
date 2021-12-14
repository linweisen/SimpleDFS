package org.simpledfs.core.context;

import org.simpledfs.core.config.Configuration;
import org.simpledfs.core.dir.IDirectory;
import org.simpledfs.core.node.NodeInfo;

import java.util.Map;

public class MetaContext implements Context{

    private Configuration config;

    private IDirectory root;

    private Map<String, NodeInfo> nodeMap;

}

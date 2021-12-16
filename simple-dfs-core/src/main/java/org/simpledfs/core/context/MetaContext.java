package org.simpledfs.core.context;

import org.simpledfs.core.config.Configuration;
import org.simpledfs.core.dir.IDirectory;
import org.simpledfs.core.dir.Snapshot;
import org.simpledfs.core.node.NodeInfo;

import java.util.HashMap;
import java.util.Map;

public class MetaContext implements Context{

    private Configuration config;

    private IDirectory root;

    private Map<String, NodeInfo> nodeMap;

    private Snapshot snapshot;

    private MetaContext(){

    }

    public void addNode(NodeInfo nodeInfo){
        if (nodeMap == null){
            nodeMap = new HashMap<>();
        }
        nodeMap.put(nodeInfo.getId(), nodeInfo);
    }

    public IDirectory getRoot() {
        return root;
    }

    public void setRoot(IDirectory root) {
        this.root = root;
    }

    public Map<String, NodeInfo> getNodeMap() {
        return nodeMap;
    }

    public Configuration getConfig() {
        return config;
    }

    public Snapshot getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Snapshot snapshot) {
        this.snapshot = snapshot;
    }

    public static class BuildContext{

        private MetaContext metaContext = new MetaContext();

        public BuildContext root(IDirectory root){
            this.metaContext.root = root;
            return this;
        }

        public BuildContext config(Configuration config){
            this.metaContext.config = config;
            return this;
        }

        public BuildContext nodeInfoMap(Map<String, NodeInfo> workInfoMap){
            this.metaContext.nodeMap = workInfoMap;
            return this;
        }

        public BuildContext snapshot(Snapshot snapshot){
            this.metaContext.snapshot = snapshot;
            return this;
        }

        public MetaContext build(){
            return metaContext;
        }
    }

}

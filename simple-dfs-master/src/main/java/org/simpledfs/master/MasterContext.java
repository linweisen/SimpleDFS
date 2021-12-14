package org.simpledfs.master;

import org.simpledfs.core.config.Configuration;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.dir.IDirectory;

import java.util.HashMap;
import java.util.Map;

public class MasterContext implements Context {

    private Configuration config;

    private IDirectory root;

    private Map<String, WorkInfo> workInfoMap;

    private MasterContext(){

    }

    public void addWork(WorkInfo workInfo){
        if (workInfoMap == null){
            workInfoMap = new HashMap<>();
        }
        workInfoMap.put(workInfo.getId(), workInfo);
    }

    public IDirectory getRoot() {
        return root;
    }

    public void setRoot(IDirectory root) {
        this.root = root;
    }

    public Map<String, WorkInfo> getWorkInfoMap() {
        return workInfoMap;
    }

    public Configuration getConfig() {
        return config;
    }

    public static class BuildContext{

        private MasterContext masterContext = new MasterContext();

        public BuildContext root(IDirectory root){
            this.masterContext.root = root;
            return this;
        }

        public BuildContext config(Configuration config){
            this.masterContext.config = config;
            return this;
        }

        public BuildContext workInfoMap(Map<String, WorkInfo> workInfoMap){
            this.masterContext.workInfoMap = workInfoMap;
            return this;
        }

        public MasterContext build(){
            return masterContext;
        }
    }
}

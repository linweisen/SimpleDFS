package org.simpledfs.master;

import java.util.Map;

public class WorkManager {

    private Map<String, WorkInfo> workInfoMap;

    private WorkManager() {
    }




    public static WorkManager getInstance() {
        return WorkManagerHolder.workManager;
    }

    private static class WorkManagerHolder {
        private static WorkManager workManager = new WorkManager();
    }
}

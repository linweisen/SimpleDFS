package org.simpledfs.core.node;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Properties;

public class DiskInfo implements SystemInfo {

    private SystemType systemType;

    private long diskSpace;

    private long idleSpace;

    private long usedSpace;

    private DecimalFormat df = new DecimalFormat("0.00");

    public DiskInfo() {
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name");
        if (osName.startsWith("Mac OS")){
            this.systemType = SystemType.MACOS;
        }

    }

    private void getWindowsDiskSpace(String path){
        if (path == null || path.isEmpty()){
            File[] roots = File.listRoots();
            for (int i = 0; i < roots.length; i++) {
                this.diskSpace += roots[i].getTotalSpace();
                this.idleSpace += roots[i].getFreeSpace();
                this.usedSpace += this.diskSpace - this.idleSpace;
            }
        }else{
            File file = new File(path);
            this.diskSpace += file.getTotalSpace();
            this.idleSpace += file.getFreeSpace();
            this.usedSpace += this.diskSpace - this.idleSpace;
        }
    }

    private void getLinuxSpace(String path){
        File file = null;
        if (path == null || path.isEmpty()){
            file = new File("/");

        }else{
            file = new File(path);
        }
        this.diskSpace = file.getTotalSpace();
        this.idleSpace = file.getUsableSpace();
        this.usedSpace = this.diskSpace - this.idleSpace;
    }

    @Override
    public void obtain() {
        switch (this.systemType){
            case WINDOWS:
                getWindowsDiskSpace(null);
                break;
            case LINUX:
            case MACOS:
                getLinuxSpace(null);
        }
    }

    @Override
    public String toString() {
        int Gb = 1000 * 1000 * 1000;
        return "DiskInfo{" +
                "diskSpace=" + diskSpace / Gb + " GB" +
                ", idleSpace=" + idleSpace / Gb + " GB" +
                ", usedSpace=" + usedSpace / Gb + " GB" +
                '}';
    }
}

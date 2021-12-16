package org.simpledfs.core.dir;

/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/15
 **/
public interface Snapshot {

    void write(IDirectory directory);

    IDirectory read();

    boolean isEmptySnapshot();
}

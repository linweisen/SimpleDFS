package org.simpledfs.core.uuid;

import java.util.UUID;

/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/14
 **/
public class DefaultUUIDGenerator implements UUIDGenerator {

    @Override
    public long getUID() {
        return UUID.randomUUID().node();
    }
}

package com.simpledfs.core;

import org.junit.Test;
import org.simpledfs.core.uuid.SnowflakeGenerator;
import org.simpledfs.core.uuid.UUIDGenerator;

/**
 *
 * @description
 *
 * @author linweisen
 * @date 2021/12/22
 * @version 1.0
**/
public class UUIDTest {

    @Test
    public void test(){
        UUIDGenerator generator = new SnowflakeGenerator(1, 1);
        System.out.println(generator.getUID());
        System.out.println(generator.getUID());
    }
}

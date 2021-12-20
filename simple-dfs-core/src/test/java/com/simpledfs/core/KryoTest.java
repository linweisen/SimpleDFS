package com.simpledfs.core;

import org.junit.Test;
import org.simpledfs.core.req.MkdirRequest;
import org.simpledfs.core.serialize.KryoSerializer;
import org.simpledfs.core.uuid.DefaultUUIDGenerator;
import org.simpledfs.core.uuid.SnowflakeGenerator;

/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/20
 **/
public class KryoTest {

    @Test
    public void test(){
        MkdirRequest request = new MkdirRequest();
        request.setParent("/user");
        request.setName("name");
//        DefaultUUIDGenerator generator = new DefaultUUIDGenerator();
//        System.out.println(generator.getUID());

        System.out.println(KryoSerializer.getInstance().serialize(request));
    }

    @Test
    public void test1(){
//        SnowflakeGenerator generator = new SnowflakeGenerator();
        long i = 20211220112334004L;
        System.out.println(i);
        String b = Long.toBinaryString(i);
        System.out.println(Long.toBinaryString(i));
        System.out.println(Integer.toBinaryString(52));
    }
}

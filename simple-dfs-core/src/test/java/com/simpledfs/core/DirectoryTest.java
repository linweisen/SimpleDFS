package com.simpledfs.core;

import org.junit.Test;
import org.simpledfs.core.excutor.MkdirRequestProcessor;
import org.simpledfs.core.req.MkdirRequest;

public class DirectoryTest {

    @Test
    public void test(){
        String s = "/a";
        String s1 = "/a/";
        MkdirRequest request = new MkdirRequest();
        request.setName("test");
        request.setParent("/");
//        MkdirRequestProcessor processor = new MkdirRequestProcessor(null, request);

    }
}

package com.simpledfs.core;

import org.junit.Before;
import org.junit.Test;
import org.simpledfs.core.dir.Directory;
import org.simpledfs.core.dir.File;
import org.simpledfs.core.dir.IDirectory;
import org.simpledfs.core.dir.INode;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.serialize.KryoSerializer;
import org.simpledfs.core.serialize.Serializer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/15
 **/
public class SnapshotTest {

    private IDirectory root;

    private Serializer serializer;

    @Before
    public void before(){
        root = new Directory("/");
        INode iNode = new INode();
        iNode.setGroup("admin");
        iNode.setUser("admin");
        iNode.setSize(0);
        iNode.setUpdateTime(System.currentTimeMillis());
        root.setINode(iNode);

        IDirectory user = new Directory("/user");
        INode userNode = new INode();
        userNode.setGroup("admin");
        userNode.setUser("admin");
        userNode.setSize(0);
        userNode.setUpdateTime(System.currentTimeMillis());
        user.setINode(userNode);

        root.addChildDirectory(user);
        serializer = KryoSerializer.getInstance();

    }

    @Test
    public void test(){
        byte[] b = root.serialize(serializer);

        IDirectory tmp = serializer.deserialize(b, IDirectory.class);
        System.out.println(tmp);
    }
}

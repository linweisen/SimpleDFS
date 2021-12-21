package com.simpledfs.core;

import org.junit.Before;
import org.junit.Test;
import org.simpledfs.core.dir.*;
import org.simpledfs.core.packet.Packet;
import org.simpledfs.core.serialize.KryoSerializer;
import org.simpledfs.core.serialize.Serializer;
import org.simpledfs.core.utils.MD5Utils;

import java.io.FileNotFoundException;
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

    private String storagePath = "/tmp/test";

    @Before
    public void before(){
        root = new Directory("/", "-1");
        INode inode = new INode();
        inode.setGroup("admin");
        inode.setUser("admin");
        inode.setSize(0);
        inode.setUpdateTime(System.currentTimeMillis());
        root.setINode(inode);
        root.setParentId("-1");
        root.setId(MD5Utils.getMD5String(root.getName()));

        IDirectory user = new Directory("/user", root.getId());
        INode userNode = new INode();
        userNode.setGroup("admin");
        userNode.setUser("admin");
        userNode.setSize(0);
        userNode.setUpdateTime(System.currentTimeMillis());
        user.setINode(userNode);
        user.setParentId(root.getId());
        user.setId(MD5Utils.getMD5String(user.getName()));
        root.addChildDirectory(user);
        serializer = KryoSerializer.getInstance();

    }

    @Test
    public void test(){
        byte[] b = root.serialize(serializer);

        IDirectory tmp = serializer.deserialize(b, IDirectory.class);
        System.out.println(tmp);
    }

    @Test
    public void storage() throws FileNotFoundException {
        Snapshot defaultSnapshot = new DefaultSnapshot(storagePath);
        defaultSnapshot.write(root);
        defaultSnapshot.write(root.getChildren().get(0));
    }

    @Test
    public void read() throws FileNotFoundException {
        Snapshot defaultSnapshot = new DefaultSnapshot(storagePath);
        IDirectory root = defaultSnapshot.read();
        System.out.println(root);
    }
}

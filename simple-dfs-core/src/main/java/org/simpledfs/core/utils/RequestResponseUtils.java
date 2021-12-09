package org.simpledfs.core.utils;

public class RequestResponseUtils {

    public static boolean isRequest(byte type){
        byte flag = (byte)0x80;
        return (flag & type) == 0;
    }

    public static void main(String[] args) {

        System.out.println(isRequest((byte)0x81));
    }
}

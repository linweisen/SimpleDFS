package com.simpledfs.core;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.math.BigInteger;
import java.net.*;
import java.util.Collections;

/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/22
 **/
public class NetTest {

    @Test
    public void test() throws UnknownHostException, SocketException {
        System.out.println(getLocalIPIndex());
    }

    private int getLocalIPIndex() throws UnknownHostException, SocketException {
        InetAddress localHost = Inet4Address.getLocalHost();
        String localIP = localHost.getHostAddress();
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);
        int mask = 0;
        for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
            if (address.getAddress().getHostAddress().equals(localIP)){
                mask = address.getNetworkPrefixLength();
            }
        }
        if (mask != 0){
            String[] seg = localIP.split("\\.");
            StringBuilder binaryIpBuilder = new StringBuilder();
            for (String s : seg){
                binaryIpBuilder.append(byteToBinary(Integer.valueOf(s)));
            }

            int i = Integer.valueOf(new BigInteger(binaryIpBuilder.substring(mask), 2).toString());
            return i;

        }else{
            return -1;
        }

    }

    private String byteToBinary(int i){
        int size = Byte.SIZE;
        String b = Integer.toBinaryString(i);
        return RandomStringUtils.random(size - b.length(), "0") + b;
    }
}

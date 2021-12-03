package org.simpledfs.client;

public class ServerInfo {

    private String address;

    private int port;

    public ServerInfo(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public ServerInfo(int port) {
        this("127.0.0.1", port);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

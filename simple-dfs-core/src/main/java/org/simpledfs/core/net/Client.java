package org.simpledfs.core.net;

import org.simpledfs.core.packet.Packet;

public interface Client {

    public void start();

    public void send(Packet packet);
}

package org.simpledfs.client;

import java.util.concurrent.CompletableFuture;
//import org.simpledfs.core

public interface Client {

    void connect();

    /**
     * send request to server
     *
     * @param request the request packet
     * @return the response future
     */
//    CompletableFuture<Packet> sendRequest(Packet request);
}

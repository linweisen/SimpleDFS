package org.simpledfs.core.packet;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;


public class PendingPackets {

    private static Map<Long, CompletableFuture<Packet>> pendingRequests = new ConcurrentHashMap<>();

    public static void add(Long id, CompletableFuture<Packet> promise) {
        pendingRequests.putIfAbsent(id, promise);
    }

    public static CompletableFuture<Packet> remove(Long id) {
        return pendingRequests.remove(id);
    }

}

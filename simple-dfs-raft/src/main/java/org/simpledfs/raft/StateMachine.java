package org.simpledfs.raft;

public interface StateMachine {

    void apply(byte[] dataBytes);
}

package org.linweisen.common.protocol;

public abstract class AbstractProtocol {

    protected byte type;

    private int length;

    public abstract byte getType();

}

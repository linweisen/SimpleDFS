package org.simpledfs.core.req;

import org.simpledfs.core.node.NodeInfo;

/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/21
 **/
public class GetWorkInfoResponse extends AbstractResponse {

    private NodeInfo nodeInfo;

    public GetWorkInfoResponse() {
        super((byte)0x83);
    }

    public NodeInfo getNodeInfo() {
        return nodeInfo;
    }

    public void setNodeInfo(NodeInfo nodeInfo) {
        this.nodeInfo = nodeInfo;
    }
}

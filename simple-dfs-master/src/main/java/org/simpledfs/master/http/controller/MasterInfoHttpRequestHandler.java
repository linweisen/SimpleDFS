package org.simpledfs.master.http.controller;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.context.MetaContext;
import org.simpledfs.core.node.NodeInfo;
import org.simpledfs.master.http.RenderType;

import java.util.Map;

public class MasterInfoHttpRequestHandler extends AbstractHttpRequestHandler {

    public MasterInfoHttpRequestHandler() {
        super("/getInfo");
    }

    @Override
    public HttpResponse handle(FullHttpRequest request, Context context) {
        MetaContext meta = (MetaContext) context;
        Map<String, NodeInfo> nodeMap = meta.getNodeMap();
        if (nodeMap == null){
            return buildResponse("no work node", RenderType.TEXT);
        }else{
            return buildResponse(nodeMap, RenderType.TEXT);
        }

    }


}

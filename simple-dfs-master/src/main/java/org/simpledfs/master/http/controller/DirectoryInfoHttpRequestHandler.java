package org.simpledfs.master.http.controller;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.simpledfs.core.context.Context;
import org.simpledfs.core.context.MetaContext;
import org.simpledfs.core.dir.IDirectory;
import org.simpledfs.core.node.NodeInfo;
import org.simpledfs.core.utils.JacksonTool;
import org.simpledfs.master.http.RenderType;

import java.util.Map;

/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/16
 **/
public class DirectoryInfoHttpRequestHandler extends AbstractHttpRequestHandler {

    public DirectoryInfoHttpRequestHandler() {
        super("/directory");
    }

    @Override
    public HttpResponse handle(FullHttpRequest request, Context context) {
        MetaContext meta = (MetaContext) context;
        IDirectory root = meta.getRoot();
        return buildResponse(JacksonTool.toJson(root), RenderType.JSON);

    }
}

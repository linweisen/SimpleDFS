package org.simpledfs.master.http.controller;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.simpledfs.master.MasterContext;
import org.simpledfs.master.WorkInfo;
import org.simpledfs.master.http.RenderType;

import java.util.Map;

public class MasterInfoHttpRequestHandler extends AbstractHttpRequestHandler {

    public MasterInfoHttpRequestHandler() {
        super("/getInfo");
    }

    @Override
    public HttpResponse handle(FullHttpRequest request, MasterContext context) {
        Map<String, WorkInfo> workInfoMap = context.getWorkInfoMap();
        if (workInfoMap == null){
            return buildResponse("no work node", RenderType.TEXT);
        }else{
            return buildResponse(workInfoMap, RenderType.TEXT);
        }

    }


}

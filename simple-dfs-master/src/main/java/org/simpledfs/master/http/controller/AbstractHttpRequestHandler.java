package org.simpledfs.master.http.controller;

import io.netty.handler.codec.http.HttpResponse;
import org.simpledfs.master.http.HttpRenderUtil;
import org.simpledfs.master.http.RenderType;

public abstract class AbstractHttpRequestHandler implements HttpRequestHandler {

    private String uri;

    public AbstractHttpRequestHandler(String uri) {
        this.uri = uri;
    }

    public String getUri(){
        return uri;
    }

    protected HttpResponse buildResponse(Object result, RenderType renderType){
        return HttpRenderUtil.render(result, renderType);
    }
}

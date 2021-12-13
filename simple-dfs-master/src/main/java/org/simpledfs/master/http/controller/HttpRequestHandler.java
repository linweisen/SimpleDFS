package org.simpledfs.master.http.controller;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.simpledfs.master.MasterContext;

public interface HttpRequestHandler {

    public HttpResponse handle(FullHttpRequest request, MasterContext context);

    public String getUri();
}

package org.simpledfs.master.http.controller;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponse;

public interface HttpRequestHandler {

    public HttpResponse handle(FullHttpRequest request);

    public String getUri();
}

package org.simpledfs.master.http.controller;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.simpledfs.core.context.Context;

public interface HttpRequestHandler {

    public HttpResponse handle(FullHttpRequest request, Context context);

    public String getUri();
}

package org.simpledfs.master.http.controller;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponse;

public class MasterInfoHttpRequestHandler extends AbstractHttpRequestHandler {

    public MasterInfoHttpRequestHandler() {
        super("/getInfo");
    }

    @Override
    public HttpResponse handle(FullHttpRequest request) {

        return null;
    }
}

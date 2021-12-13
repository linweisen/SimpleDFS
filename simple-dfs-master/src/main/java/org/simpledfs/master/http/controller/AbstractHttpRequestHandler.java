package org.simpledfs.master.http.controller;

public abstract class AbstractHttpRequestHandler implements HttpRequestHandler {

    private String uri;

    public AbstractHttpRequestHandler(String uri) {
        this.uri = uri;
    }

    public String getUri(){
        return uri;
    }
}

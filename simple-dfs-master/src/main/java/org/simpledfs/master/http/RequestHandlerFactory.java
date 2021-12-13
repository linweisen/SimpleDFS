package org.simpledfs.master.http;

import org.simpledfs.core.utils.ClassUtils;
import org.simpledfs.master.http.controller.HttpRequestHandler;

import java.io.IOException;
import java.util.*;

public class RequestHandlerFactory {

    private Map<String, HttpRequestHandler> handlerMap;

    private RequestHandlerFactory() {
        handlerMap = new HashMap<>();

        String packageName = "org.simpledfs.master.http.controller";
        try {
            Set<Class<?>> classSet = ClassUtils.getClasses(packageName);
            for (Class clazz : classSet){
                if (clazz.getSuperclass() == null){
                    continue;
                }
                if (clazz.getSuperclass().getSimpleName().equals("AbstractHttpRequestHandler")){
                    HttpRequestHandler handler = (HttpRequestHandler)clazz.newInstance();
                    handlerMap.put(handler.getUri(), handler);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    public static RequestHandlerFactory getInstance(){
        return RequestHandlerFactoryHolder.factory;
    }

    public HttpRequestHandler getHandler(String uri){
        return handlerMap.get(uri);
    }

    private static class RequestHandlerFactoryHolder{
        private static RequestHandlerFactory factory = new RequestHandlerFactory();
    }
}

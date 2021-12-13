package org.simpledfs.core.utils;

public final class Singleton {

    private String name;



    private Singleton() {
    }

    private Singleton(String name){
        this.name = name;
    }

    public static Singleton getInstance(){
        return SingletonHolder.singleton;
    }

    private static class SingletonHolder{
        private static Singleton singleton = new Singleton();


    }
}

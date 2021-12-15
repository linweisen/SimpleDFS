package org.simpledfs.work;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/15
 **/
public class GlobalRequestCounter {

    private AtomicLong counter = new AtomicLong(0L);

    private GlobalRequestCounter(){

    }

    public static long count(){
        return getInstance().counter.getAndIncrement();
    }

    public static GlobalRequestCounter getInstance(){
        return Holder.counter;
    }

    private static class Holder{
        private static GlobalRequestCounter counter = new GlobalRequestCounter();
    }
}

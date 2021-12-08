package org.simpledfs.core.dir;

import org.simpledfs.core.exception.NonDirectoryException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DirectoryLock {

    private Map<String, ReadWriteLock> lockMap;

    private DirectoryLock(){
        lockMap = new ConcurrentHashMap<>();
    }

    public static DirectoryLock getInstance(){
        return DirectoryLockHolder.directoryLock;
    }

    public void lock(String directory){
        ReadWriteLock lock = lockMap.get(directory);
        if (lock == null){
            throw new NonDirectoryException();
        }
        lock.readLock();
    }

    public void unlock(String directory){
        ReadWriteLock lock = lockMap.get(directory);
        if (lock == null){
            throw new NonDirectoryException();
        }
        lock.writeLock().unlock();
    }

    public void addLock(String directory){
        if (lockMap.containsKey(directory)){
            throw new NonDirectoryException();
        }
        lockMap.put(directory, new ReentrantReadWriteLock());
    }

    public void removeLock(String directory){
        if (lockMap.containsKey(directory)){
            throw new NonDirectoryException();
        }
        lockMap.remove(directory);
    }

    private static class DirectoryLockHolder {
        private static DirectoryLock directoryLock = new DirectoryLock();
    }
}

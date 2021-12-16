package org.simpledfs.core.exception.directory;

/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/16
 **/
public class DirectoryAlreadyExistException extends RuntimeException{


    public DirectoryAlreadyExistException(String message) {
        super(message);
    }


}

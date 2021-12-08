package org.simpledfs.core.excutor;


/**
 * @author houyi
 */
public interface Actuator<T> {


    T execute(Object... request);


}

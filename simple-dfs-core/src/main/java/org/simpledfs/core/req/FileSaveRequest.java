package org.simpledfs.core.req;

/**
 * @author linweisen
 * @version 1.0
 * @description
 * @date 2021/12/21
 **/
public class FileSaveRequest extends AbstractRequest {

    public FileSaveRequest() {
        super((byte)0x02);
    }
}

package com.gi.ctdn.cloud.storage.vo;

import com.gi.ctdn.cloud.storage.param.FileParam;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vincent
 * Date: 17-2-10
 * Time: 下午1:30
 * Package: com.gi.ctdn.cloud.storage.vo
 * Company: 星河互联
 * Group:   创投大脑
 */
public class FileUploadRestult implements Serializable{

    private Boolean isSuccess;

    private String msg ;

    private List<FileParam> fileParams;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<FileParam> getFileParams() {
        return fileParams;
    }

    public void setFileParams(List<FileParam> fileParams) {
        this.fileParams = fileParams;
    }
}

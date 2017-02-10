package com.gi.ctdn.cloud.storage.param;

import lombok.Data;

import java.io.Serializable;


public class FileParam implements Serializable{

	
	/**
	 * 
	 */

	private Boolean isSuccess;
	private String path; //文件大小
	private Long time;
	private String name;
	private String error;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}

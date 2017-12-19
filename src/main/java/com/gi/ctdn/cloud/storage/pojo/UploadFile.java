package com.gi.ctdn.cloud.storage.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UploadFile {

    @JsonIgnore
    private int id;
    @JsonIgnore
    private String userCode;
    @JsonIgnore
    private String userType;
    @JsonIgnore
    private String userRole;
    private String url;
    @JsonIgnore
    private String bucketName;

    private long fileLength;
    private String fileName;
    @JsonIgnore
    private String fileUploadName;
    @JsonIgnore
    private String fileSuffix;
    @JsonIgnore
    private String eTag;
    private long createdTime;
    @JsonIgnore
    private long status;


}

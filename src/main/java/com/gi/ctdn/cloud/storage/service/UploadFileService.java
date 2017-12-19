package com.gi.ctdn.cloud.storage.service;

import com.gi.ctdn.cloud.storage.mapper.UploadFileMapper;
import com.gi.ctdn.cloud.storage.pojo.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadFileService {

    @Autowired
    private UploadFileMapper uploadFileMapper;

    public int batchAdd(List<UploadFile> uploadFiles){
        return uploadFileMapper.batchAdd(uploadFiles);
    }
}

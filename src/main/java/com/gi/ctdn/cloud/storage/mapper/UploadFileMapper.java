package com.gi.ctdn.cloud.storage.mapper;

import com.gi.ctdn.cloud.storage.pojo.UploadFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UploadFileMapper {

    @Insert("<script>"+
            "insert into upload_file(user_code,user_type,user_role,url,bucket_name,file_length,file_name,file_upload_name,file_suffix,etag,status,created_time) "
            + "<foreach collection =\"uploadFiles\" item=\"uploadFile\" index= \"index\" separator =\",\"> "
            + "values(#{uploadFile.userCode},#{uploadFile.userType},#{uploadFile.userRole},#{uploadFile.url},#{uploadFile.bucketName},#{uploadFile.fileLength},#{uploadFile.fileName},#{uploadFile.fileUploadName},#{uploadFile.fileSuffix},#{uploadFile.eTag},#{uploadFile.status},#{uploadFile.createdTime}) "
            + "</foreach > "
            + "</script>")
    int batchAdd(@Param("uploadFiles") List<UploadFile> uploadFiles);

}

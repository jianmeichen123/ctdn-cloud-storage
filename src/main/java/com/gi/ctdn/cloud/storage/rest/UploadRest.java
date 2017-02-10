package com.gi.ctdn.cloud.storage.rest;

import com.gi.ctdn.cloud.storage.param.FileParam;
import com.gi.ctdn.cloud.storage.vo.FileUploadRestult;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.*;

/**
 * Created by vincent on 17-1-10.
 */
@Controller
@RequestMapping("/upload")
public class UploadRest {

    @RequestMapping(value="/image")
    public @ResponseBody FileUploadRestult imageUpload(@RequestParam("file") MultipartFile[] files){
        String fileName = null;
        List<FileParam> fileParams = new ArrayList<>();

        FileUploadRestult fur = new FileUploadRestult();
        if (files == null || files.length ==0){
            fur.setSuccess(false);
            fur.setMsg("上传文件不能为空");
        }else{
            for(MultipartFile file:files){
                try {
                    fileName = file.getOriginalFilename();
                    String postfix = fileName.split(".")[fileName.split(".").length-1];
                    byte[] bytes = file.getBytes();
                    String path = "/tmp/image/" + getUUID();
                    BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File(path+postfix)));
                    buffStream.write(bytes);
                    buffStream.close();

                    FileParam fileParam = new FileParam();
                    fileParam.setPath(path);
                    fileParam.setSuccess(true);
                    fileParam.setName(fileName);
                    fileParam.setTime(System.currentTimeMillis());
                    fileParams.add(fileParam);

                    Thumbnails.of(path).forceSize(512, 512).toFile(path+"@512");
                    Thumbnails.of(path).forceSize(37, 37).toFile(path+"@37");

                } catch (Exception e) {

                    FileParam fileParam = new FileParam();
                    fileParam.setSuccess(true);
                    fileParam.setName(fileName);
                    fileParam.setTime(System.currentTimeMillis());
                    fileParam.setError(e.getMessage());
                    fileParams.add(fileParam);

                }
            }
            fur.setMsg("成功");
            fur.setSuccess(true);
            fur.setFileParams(fileParams);
        }
        return fur;
    }

    @RequestMapping(value="/file" )
    public @ResponseBody FileUploadRestult file(@RequestParam("file") MultipartFile[] files){
        String fileName = null;
        List<FileParam> fileParams = new ArrayList<>();

        FileUploadRestult fur = new FileUploadRestult();
        if (files == null || files.length ==0){
            fur.setSuccess(false);
            fur.setMsg("上传文件不能为空");
        }else{
            for(MultipartFile file:files){
                try {
                    fileName = file.getOriginalFilename();
                    byte[] bytes = file.getBytes();
                    String path = "/tmp/file/" + fileName;
                    BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File(path)));
                    buffStream.write(bytes);
                    buffStream.close();

                    FileParam fileParam = new FileParam();
                    fileParam.setPath(path);
                    fileParam.setSuccess(true);
                    fileParam.setName(fileName);
                    fileParam.setTime(System.currentTimeMillis());
                    fileParams.add(fileParam);


                } catch (Exception e) {

                    FileParam fileParam = new FileParam();
                    fileParam.setSuccess(true);
                    fileParam.setName(fileName);
                    fileParam.setTime(System.currentTimeMillis());
                    fileParam.setError(e.getMessage());
                    fileParams.add(fileParam);

                }
            }
            fur.setMsg("成功");
            fur.setSuccess(true);
            fur.setFileParams(fileParams);
        }
        return fur;
    }

    private String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
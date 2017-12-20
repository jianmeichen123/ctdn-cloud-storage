package com.gi.ctdn.cloud.storage.rest;

import com.gi.ctdn.cloud.storage.pojo.UploadFile;
import com.gi.ctdn.cloud.storage.service.UploadFileService;
import com.gi.ctdn.cloud.storage.util.AliOSSUtil;
import com.gi.ctdn.cloud.storage.vo.FileUploadRestult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
public class UploadRest {

    @Autowired
    private AliOSSUtil aliOSSUtil;

    @Autowired
    private UploadFileService uploadFileService;

    @ResponseBody
    @RequestMapping(value = "/upload/{path}")
    public FileUploadRestult upload(HttpServletRequest request,@PathVariable("path") String path)  {
        FileUploadRestult uploadRestult = new FileUploadRestult();
        List<UploadFile> UploadFiles = new ArrayList<>();
        uploadRestult.setUploadFiles(UploadFiles);
        String userCode = "test";

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    //取得当前上传文件的文件名称
                    String filename = file.getOriginalFilename();
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (!filename.trim().equals("")) {
                        //重命名上传后的文件名
                        String filekey = String.valueOf(UUID.randomUUID().toString().replaceAll("-",""));
                        UploadFile uploadFile = new UploadFile();
                        uploadFile.setFileName(filename);
                        uploadFile.setUserCode(userCode);
                        uploadFile.setUserRole("test");
                        uploadFile.setUserType("ctdn");
                        uploadFile.setBucketName("ctdn");
                        uploadFile.setFileSuffix("");
                        uploadFile.setUrl("");
                        uploadFile.setFileLength(file.getSize());
                        uploadFile.setFileUploadName(filekey);
                        try {
                            Map<String,String> res = aliOSSUtil.upload(file.getInputStream(),filekey,"image".equals(path)?true:false);
                            uploadFile.setETag(res.get("etag"));
                            uploadFile.setUrl(res.get("url"));
                            uploadFile.setStatus(0);
                        } catch (IOException e) {
                            uploadFile.setETag("");
                            uploadFile.setStatus(1);
                        }
                        UploadFiles.add(uploadFile);
                    }
                }

            }
            if(UploadFiles.size()>0){
                uploadFileService.batchAdd(UploadFiles);
            }

        }
        request.removeAttribute("user");
        return uploadRestult;
    }}

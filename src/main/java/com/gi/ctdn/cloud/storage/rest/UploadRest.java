package com.gi.ctdn.cloud.storage.rest;

import com.gi.ctdn.cloud.storage.pojo.UploadFile;
import com.gi.ctdn.cloud.storage.service.UploadFileService;
import com.gi.ctdn.cloud.storage.util.AliOSSUtil;
import com.gi.ctdn.cloud.storage.util.IdGenerator;
import com.gi.ctdn.cloud.storage.vo.FileUploadRestult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class UploadRest {

    @Autowired
    private AliOSSUtil aliOSSUtil;

    @Autowired
    private UploadFileService uploadFileService;

   MessageDigest md5 = MessageDigest.getInstance("MD5");

    public UploadRest() throws NoSuchAlgorithmException {
    }

    @ResponseBody
    @RequestMapping(value = "/upload")
    public FileUploadRestult upload(HttpServletRequest request)  {
        FileUploadRestult uploadRestult = new FileUploadRestult();
        List<UploadFile> UploadFiles = new ArrayList<>();
        uploadRestult.setUploadFiles(UploadFiles);
        String ssiod = request.getSession().toString();
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
                        String filekey = String.valueOf(IdGenerator.generateId(AliOSSUtil.class));
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
                            uploadFile.setETag(aliOSSUtil.upload(file.getInputStream(),uploadFile.getFileUploadName()).getETag());
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
        return uploadRestult;
    }
}

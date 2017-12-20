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
        String code = (String) request.getAttribute("CODE");
        String userType = (String) request.getAttribute("USERTYPE");
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    String filename = file.getOriginalFilename();
                    if (!filename.trim().equals("")) {
                        String filekey = String.valueOf(UUID.randomUUID().toString().replaceAll("-",""));
                        UploadFile uploadFile = new UploadFile();
                        uploadFile.setFileName(filename);
                        uploadFile.setUserCode(code);
                        uploadFile.setUserRole("");
                        uploadFile.setUserType(userType);
                        uploadFile.setFileSuffix(filename.substring(filename.lastIndexOf(".") + 1));
                        uploadFile.setCreatedTime(System.currentTimeMillis());
                        uploadFile.setFileLength(file.getSize());
                        uploadFile.setFileUploadName(filekey);
                        try {
                            Map<String,String> res = aliOSSUtil.upload(file.getInputStream(),filekey,"image".equals(path)?true:false);
                            uploadFile.setETag(res.get("etag"));
                            uploadFile.setUrl(res.get("url")!=null?res.get("url"):"");
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
                try{

                    uploadFileService.batchAdd(UploadFiles);
                } catch (Exception e) {
                    uploadRestult.setSuccess(false);
                    uploadRestult.setMsg("服务器繁忙,请稍后！");
                }
            }

        }
        request.removeAttribute("user");
        return uploadRestult;
    }}

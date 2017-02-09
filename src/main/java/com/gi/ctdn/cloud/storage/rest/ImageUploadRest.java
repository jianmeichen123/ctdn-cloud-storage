package com.gi.ctdn.cloud.storage.rest;

import com.alibaba.fastjson.JSON;
import com.gi.ctdn.cloud.storage.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * Created by vincent on 17-1-10.
 */
@Controller
@RequestMapping("/upload")
public class ImageUploadRest {

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestHeader(name = "userInfo" ,required = false) String userInfo,@RequestParam(value = "files[]", required = false) MultipartFile[] fileUploads, @RequestParam(value = "params", required = false) String params) throws Exception {
        User user =  null;
        if (userInfo!=null && userInfo !=""){
            user = JSON.parseObject(URLDecoder.decode(userInfo,"UTF-8"), User.class);
        }
        return "";
    }
}
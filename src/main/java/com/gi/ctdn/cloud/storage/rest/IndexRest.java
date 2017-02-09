package com.gi.ctdn.cloud.storage.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by vincent on 17-1-11.
 */
@Controller
public class IndexRest {

    /**
     */
    @RequestMapping("/")
    public String upload(HttpServletResponse response) {
        return "upload";
    }
}

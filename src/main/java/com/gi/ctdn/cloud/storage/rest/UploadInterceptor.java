package com.gi.ctdn.cloud.storage.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploadInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies==null||cookies.length==0){
            return false;
        }
        String uid = null;
        String s = null;
        for(Cookie cookie:cookies){
            if (cookie.getName().equals("_uid_")){
                uid = cookie.getValue();
            }
            if (cookie.getName().equals("s_")){
                s = cookie.getValue();
            }
        }
        if (uid==null||s==null){
            return false;
        }
        String key = "ctdn:"+s+":"+uid+":code";
        String code = stringRedisTemplate.boundValueOps(key).get();
        if (code==null){
            return false;
        }
        httpServletRequest.setAttribute("CODE",code);
        httpServletRequest.setAttribute("USERTYPE",s);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

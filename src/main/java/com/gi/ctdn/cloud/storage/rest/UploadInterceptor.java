package com.gi.ctdn.cloud.storage.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gi.ctdn.cloud.storage.vo.FileUploadRestult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploadInterceptor implements HandlerInterceptor {

    private static FileUploadRestult nologin = new FileUploadRestult(false,"未登录或无权限");

    private static  String  nologinStr ;

    static {
        try {
            nologinStr = new ObjectMapper().writeValueAsString(nologin);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        String adminkey  = httpServletRequest.getHeader("key");
//        if (adminkey!=null&&adminkey.equals("testjet")){
//            httpServletRequest.setAttribute("CODE","admin");
//            httpServletRequest.setAttribute("USERTYPE","admin");
//            return true;
//        }
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies==null||cookies.length==0){
            //writeLoginMsg(httpServletResponse);
            httpServletRequest.setAttribute("CODE","test");
            httpServletRequest.setAttribute("USERTYPE","test");
            return true;
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
            writeLoginMsg(httpServletResponse);
            return false;
        }
        String key = "ctdn:"+s+":"+uid+":code";
        String code = stringRedisTemplate.boundValueOps(key).get();
        if (code==null){
            writeLoginMsg(httpServletResponse);
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

    private void writeLoginMsg(HttpServletResponse res) throws Exception {
        res.setHeader("Content-type", "application/json;charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(nologinStr);
    }
    private void writeLoginMsg(HttpServletRequest req, HttpServletResponse res, String msg) throws Exception {
        res.setHeader("Content-type", "application/json;charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(nologinStr);
    }
}

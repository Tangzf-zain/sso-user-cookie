package com.bz.controller;

import com.bz.entity.User;
import com.bz.utils.LoginCacheUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

/**
 * 页面跳转逻辑
 */
@Controller
@RequestMapping("/view")
public class ViewController {

    @GetMapping("/login")
    public String toLogin(@RequestParam(required = false,defaultValue = "") String target,
                          HttpSession session, @CookieValue(required = false,value = "TOKEN") Cookie cookie){

        if (StringUtils.isEmpty(target)){
            target = "http://www.codeshop.com:9010";
        }
        if ( cookie != null){
            String value = cookie.getValue();
            User user = LoginCacheUtil.loginUser.get(value);
            if (user != null){
                return "redirect:" +target;
            }
        }
        //1.在此处需要验证地址的正确性

        //重定向地址
        session.setAttribute("target",target);
        return "login";
    }
}

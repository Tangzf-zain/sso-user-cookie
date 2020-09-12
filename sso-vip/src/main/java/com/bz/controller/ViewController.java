package com.bz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    private RestTemplate restTemplate;

    private final String LOGIN_INFO_ADDRESS="http://login.codeshop.com:9000/login/info?token=";

    @GetMapping("/index")
    public String toIndex(@CookieValue(required = false,value = "TOKEN") Cookie cookie, HttpSession session){
        //1. 获取token, 2远程服务器调用是否正确
        if (cookie != null){
            String token = cookie.getValue();
            Map result = restTemplate.getForObject(LOGIN_INFO_ADDRESS + token, Map.class);
            if (result != null){
                session.setAttribute("loginUser",result);
            }
        }
        return "index";
    }
}

package com.bz.controller;

import com.bz.entity.User;
import com.bz.utils.LoginCacheUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/login")
public class LoginController {
    private static Set<User> dbUsers;
    static {
        dbUsers = new HashSet<>();
        dbUsers.add(new User(0,"zhangsan","12345"));
        dbUsers.add(new User(1,"lisi","12345"));
        dbUsers.add(new User(2,"wangwu","12345"));
    }

    @PostMapping
    public String doLogin(User user, HttpSession session, HttpServletResponse response){
        //1.取出session 验证数据的正确性
        String target = (String) session.getAttribute("target");
        //查询数据库是否正确
        Optional<User> first = dbUsers.stream().filter(dbUsers -> dbUsers.getUserName().equals(user.getUserName())
                && dbUsers.getPassWord().equals(user.getPassWord())).findFirst();
        //判断当前用户是否登录
        if (first.isPresent()){
            //保存用户
            String token = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("TOKEN",token);
            cookie.setDomain("codeshop.com");//设置相同域
            response.addCookie(cookie);
            LoginCacheUtil.loginUser.put(token,first.get());
        }else {
            //登录失败
            session.setAttribute("msg","用户名或密码错误");
            return "login";
        }

        return "redirect:"+target;
    }

    //1.在请求时，先更加cookie判断用户是否登录
    @RequestMapping("/info")
    @ResponseBody
    public ResponseEntity<User> getUserInfo(String token){
        if (!StringUtils.isEmpty(token)){
            User user = LoginCacheUtil.loginUser.get(token);
            return ResponseEntity.ok(user);
        }else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //退出这里  1：拿到当前系统的cookie 设置过期时间为0  ，响应回去  cookie不存 即可
}

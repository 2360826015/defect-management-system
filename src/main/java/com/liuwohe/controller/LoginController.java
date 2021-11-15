package com.liuwohe.controller;


import com.liuwohe.entity.EmpEntity;
import com.liuwohe.entity.Result;
import com.liuwohe.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    EmpService empService;

    //跳转到自定义登录页面
    @GetMapping("/index")
    public String index(){
        return "/login";
    }


    @GetMapping("/login")
    public ModelAndView userLogin(Authentication authentication, ModelAndView modelAndView){
        // 获取登陆成功后的用户名，查询用户信息并封装结果返回
        String username = authentication.getName();
        System.out.println("emp:::"+username);
        //调用登录方法，验证后返回消息和用户信息
        Result result = empService.login(username);
        if("400".equals(result.getCode())){
            modelAndView.addObject("error",result.getMsg());
            modelAndView.setViewName("login");
            return modelAndView;
        }
        modelAndView.addObject("success",result);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    //跳转到自定权限不足页面
    @GetMapping("/403")
    public String error(){
        return "/403";
    }

}

package com.liuwohe.controller;


import com.liuwohe.entity.EmpEntity;
import com.liuwohe.entity.Result;
import com.liuwohe.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    EmpService empService;

    @GetMapping("/index")
    public String index(){
        return "/login";
    }

    @GetMapping("/login")
    public ModelAndView test(EmpEntity emp, ModelAndView modelAndView){
        System.out.println(emp);
        //调用登录方法，验证后返回消息和用户信息
        Result result = empService.login(emp);
        if("400".equals(result.getCode())){
            modelAndView.addObject("error",result.getMsg());
            modelAndView.setViewName("login");
            return modelAndView;
        }
        modelAndView.addObject("success",result);
        modelAndView.setViewName("index");
        return modelAndView;
    }
}

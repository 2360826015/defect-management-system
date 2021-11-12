//package com.liuwohe.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import javax.annotation.Resource;
//
//@Configuration
//@EnableWebSecurity
//public class SecuriyConfig extends WebSecurityConfigurerAdapter {
//
//    @Resource
//    LoginProvider loginProvider;
//
//    //登录成功handler
//    @Resource
//    private LoginSuccessHandler loginSuccessHandler;
//    //登录失败handler
//    @Resource
//    private LoginFailureHandler loginFailureHandler;
//
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .authorizeRequests()
//                    .antMatchers("/index").permitAll()
//                    .antMatchers("/assets/**").permitAll()
//                    .anyRequest()//任何其它请求
//                    .authenticated() //都需要身份认证
//                    .and()
//                    .formLogin()//使用表单认证方式
//                    .successHandler(loginSuccessHandler)//成功登录处理器
//                    .failureHandler(loginFailureHandler)//失败登录处理器
//                    .loginPage("/index")//登录页面url
//                    .permitAll()
//                    .and()
//                    .csrf().disable();
//        }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //这里要设置自定义认证
//        auth.authenticationProvider(loginProvider);
//    }
//
//    /**
//     * BCrypt加密
//     * @return
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
//

package com.liuwohe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecuriyConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetils myUserDetils;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/assets/**").permitAll()
                    //管理人员权限访问
                    .antMatchers("/user/manger/**","/user/inspection/statistics/**").hasAuthority("manger")
                    .antMatchers("/user/censor/**").hasAuthority("censor")
                    .antMatchers("/user/inspection/defect/**","/user/inspection/send/**").hasAuthority("inspection")
                    .anyRequest()//任何其它请求
                    .authenticated() //都需要身份认证
                    .and()
                    .formLogin()//使用表单认证方式
                    .loginPage("/index")//自定义的登录页面
                    .loginProcessingUrl("/user/login") //接收登录请求的路径
                    .defaultSuccessUrl("/login") //登录成功默认页面
                    .permitAll()
                    .and()
                    .csrf().disable();
            //配置权限不够的显示页面
            http.exceptionHandling().accessDeniedPage("/403");
        }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //这里要设置自定义认证
        auth.userDetailsService(myUserDetils).passwordEncoder(password());
        }

    /**
     * BCrypt加密
     * @return
     */
    @Bean
    public PasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }
}


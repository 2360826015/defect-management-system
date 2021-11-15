package com.liuwohe.config;


import com.liuwohe.entity.EmpEntity;
import com.liuwohe.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

//自定义登录校验
@Service
public class MyUserDetils implements UserDetailsService {
    @Autowired
    private EmpService empService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //调用业务层方法根据用户名查询并返回信息
        EmpEntity user = empService.loadUserByUsername(s);
        if(user==null){
            throw new UsernameNotFoundException("用户不存在");
        }
        //设置角色权限
        List<GrantedAuthority> auths= AuthorityUtils.commaSeparatedStringToAuthorityList(user.getUserRole());
        //返回的对象中得到用户名和密码
        return new User(user.getUsername(),new BCryptPasswordEncoder().encode(user.getPhone()),auths);

    }
}

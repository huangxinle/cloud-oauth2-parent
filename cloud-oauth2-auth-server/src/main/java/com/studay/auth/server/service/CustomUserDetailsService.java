package com.studay.auth.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    //这里写死的，也可以从数据库查询
    @Override
    public UserDetails loadUserByUsername(String u) throws UsernameNotFoundException {
        return new User("admin", passwordEncoder.encode("1234"),
                AuthorityUtils.commaSeparatedStringToAuthorityList("product,product:list"));
    }
}

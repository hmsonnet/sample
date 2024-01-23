package org.example.security;

import org.example.user.service.User;
import org.example.user.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Resource(name = "userService")
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username); // 사용자 정보 조회
        return org.springframework.security.core.userdetails.User // UserDetails 객체 생성
                .builder()  // 빌더 패턴
                .username(user.getUsername())   // 사용자 이름
                .password(user.getPassword())   // 사용자 비밀번호
                .roles(user.getUserAuthorities().get(0).getAuthority().getRole())   // 사용자 권한
                .build();  // UserDetails 객체 생성
    }
}

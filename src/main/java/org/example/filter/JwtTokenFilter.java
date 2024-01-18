package org.example.filter;

import org.example.util.JwtTokenUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {   // 인증이 되었을 때만
            // 사용자 정보 얻기
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String token = jwtTokenUtil.generateToken(userDetails);

                response.addHeader("Authorization", "Bearer " + token);
                // 여기서 필요한 다른 세션 정보를 얻을 수 있음
            }
        }*/

        chain.doFilter(request, response);
    }

}

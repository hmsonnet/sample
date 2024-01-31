package org.example.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = extractToken(request);


        if (validateToken(token)) {
            Claim claim = JWT.decode(token).getClaim("username");
            String username = claim.asString();

            Claim claim2 = JWT.decode(token).getClaim("role");
            if (claim2 == null) {
                filterChain.doFilter(request, response);
                return;
            }

            String role = claim2.asString();

            List<String> role2 = new ArrayList<>();
            role2.add(role);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    username
                    , null
                    , role2.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        // 요청에서 토큰을 추출하는 로직
        // 토큰이 없으면 null을 반환
        // 토큰이 있다면 토큰을 반환
        // 토큰이 Bearer로 시작하지 않으면 null을 반환
        // 토큰이 Bearer로 시작한다면 Bearer를 제거하고 토큰을 반환

        String bearerToken = request.getHeader("Authorization");
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            return null;
        } else {
            return bearerToken.substring(7);
        }
    }

    private boolean validateToken(String token) {
        try {

            // 토큰을 검증할 알고리즘 및 시크릿 키 설정
            Algorithm algorithm = Algorithm.HMAC256("ASDFASFQWER342524356s42364sfqewr23563425GSDFGDFBGFSDFGSDFGDSFGDSFGWERTQQWEAZSXCVX");

            // JWT 디코딩 및 검증
            JWT.require(algorithm)
                    .build()
                    .verify(token);

            // 여기서 추가적인 검증 로직을 수행할 수 있음

            return true; // 유효한 토큰
        } catch (Exception e) {
            return false; // 유효하지 않은 토큰 또는 검증 실패
        }
    }

    private Authentication createAuthentication(String token) {

        // 토큰을 파싱해서 Authentication 객체를 생성하는 로직
        // 토큰에서 username을 추출해서 User 객체를 생성

        // User 객체를 기반으로 UsernamePasswordAuthenticationToken 객체를 생성
        // UsernamePasswordAuthenticationToken 객체를 반환



        return null;
    }
}

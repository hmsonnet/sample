package org.example.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String SECRET_KEY = "ASDFASFQWER342524356s42364sfqewr23563425GSDFGDFBGFSDFGSDFGDSFGDSFGWERTQQWEAZSXCVX";

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final AuthenticationManager authenticationManager;

    public JsonAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        setFilterProcessesUrl("/actionLogin");  // 로그인 요청 url 설정
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        Map<String, String> jsonAuthentication = null;
        try {
            jsonAuthentication = objectMapper.readValue(request.getInputStream(), Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String username = jsonAuthentication.get("username");
        String password = jsonAuthentication.get("password");

        // request의 body에서 username과 password를 파싱해서 UsernamePasswordAuthenticationToken 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(username, password);


        setDetails(request, authenticationToken); // authenticationToken에 요청에 대한 정보를 설정

        // 생성된 토큰을 AuthenticationManager에게 전달
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        // 토큰 만료 시간 설정
        Date expireTime = new Date(System.currentTimeMillis() + (1000 * 60 * 60)); // 1시간

        // 헤더 설정
        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("typ", "JWT");
        headerClaims.put("alg", "HS256");

        // 토큰 생성
        String token = JWT.create()
                .withHeader(headerClaims) // 헤더 설정
                .withClaim("username", authResult.getName()) // payload에 저장될 정보
                .withClaim("role", authResult.getAuthorities().toArray()[0].toString()) // payload에 저장될 정보
                .withExpiresAt(expireTime) // 만료 시간 설정
                .sign(Algorithm.HMAC256(SECRET_KEY)); // 시크릿 키를 이용한 서명


        // 토큰을 response의 헤더에 저장
        response.addHeader("Authorization", "Bearer " + token);

        // 토큰을 세션에 저장
        //request.getSession().setAttribute("token", token);

        //super.successfulAuthentication(request, response, chain, authResult);
        //chain.doFilter(request, response);
    }
}

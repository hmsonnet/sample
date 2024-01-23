package org.example.config;

import org.example.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    // 생성자를 통한 의존성 주입
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
       this.customUserDetailsService = customUserDetailsService;
       this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> web.ignoring().antMatchers(
                "/favicon.ico"
                , "/css/**"
                , "/js/**"
                , "/img/**"
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        /*
        AuthenticationManagerBuilder amb = http.getSharedObject(AuthenticationManagerBuilder.class ); // AuthenticationManagerBuilder 객체 가져오기
        AuthenticationManager authenticationManager = amb.build(); // AuthenticationManager 객체 생성

        http.authenticationManager(authenticationManager); // AuthenticationManager 설정
         */

        return http
                    .cors().and().csrf().disable()  // csrf 토큰 비활성화

                    //.addFilterBefore(new JsonAuthenticationFilter(authenticationManager)
                    //        , UsernamePasswordAuthenticationFilter.class) // 로그인 처리를 위한 필터 추가

                    .authorizeRequests(authorize -> authorize   // 요청에 대한 권한 설정
                            .antMatchers("/admin/**").hasRole("ADMIN") // admin/** 경로는 ADMIN 권한이 있어야 접근 가능
                            .antMatchers("/user/**").hasRole("USER") // user/** 경로는 USER 권한이 있어야 접근 가능
                            .antMatchers("/index").hasRole("USER") // index 경로는 USER 권한이 있어야 접근 가능
                            .anyRequest().authenticated() // 그 외의 요청은 인증만 되어 있으면 접근 가능
                    )

                    .formLogin(formLogin -> formLogin // 로그인 설정
                            .loginPage("/login")        // 로그인 페이지 경로
                            .loginProcessingUrl("/actionLogin") // 로그인 폼 action 경로
                            .defaultSuccessUrl("/index")    // 로그인 성공 시 경로
                            .failureUrl("/login?error") // 로그인 실패 시 경로
                            .permitAll()           // 로그인 페이지는 누구나 접근 가능
                    )

                    .logout(logout -> logout
                            .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 경로
                            .logoutSuccessUrl("/login?logout") // 로그아웃 성공 후 경로
                    )

                    .build();
    }
}

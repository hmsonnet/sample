package org.example.config;

import org.example.filter.JwtTokenFilter;
import org.example.security.CustomUserDetailsService;
import org.example.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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

    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    public void configureSuccess(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> web.ignoring().antMatchers(
                "/"
                , "/index.html"
                , "/favicon.ico"
                , "/css/**"
                , "/js/**"
                , "/img/**"
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .cors().and().csrf().disable()
                    .addFilterBefore(new JwtTokenFilter(jwtTokenUtil), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests(authorize -> authorize
                            .antMatchers("/admin/**").hasRole("ADMIN")
                            .antMatchers("/user/**").hasRole("USER")
                            .anyRequest().authenticated()
                    )
                    .formLogin(formLogin -> formLogin
                            .loginPage("/login")
                            .loginProcessingUrl("/actionLogin")
                            .successHandler(authenticationSuccessHandler)
                            .failureUrl("/login?error")
                            .permitAll()
                    )
                    .logout(logout -> logout
                            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                            .logoutSuccessUrl("/")
                    )
                    .build();
    }
}

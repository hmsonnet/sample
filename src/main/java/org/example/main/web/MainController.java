package org.example.main.web;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/index")
    public String index() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // 사용자 정보 얻기
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                System.out.println("Username: " + userDetails.getUsername());
                System.out.println("Authorities: " + userDetails.getAuthorities());
                // 여기서 필요한 다른 세션 정보를 얻을 수 있음
            }
        }

        return "index";
    }
}

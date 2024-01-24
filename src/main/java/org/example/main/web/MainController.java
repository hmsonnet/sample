package org.example.main.web;


import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {

    Logger logger = org.slf4j.LoggerFactory.getLogger(MainController.class);

    @GetMapping({"/index", "/"})
    public String index(
            HttpServletRequest request
            , HttpServletResponse response
            , Model model
    ) {

        /*
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // 사용자 정보 얻기
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                logger.info("Username: {}", userDetails.getUsername());
                logger.info("Authorities: {}", userDetails.getAuthorities());
                // 여기서 필요한 다른 세션 정보를 얻을 수 있음
            }
        }*/

        return "/index";
    }
}

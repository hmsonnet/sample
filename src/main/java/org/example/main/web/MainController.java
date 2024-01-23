package org.example.main.web;


import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {

    Logger logger = org.slf4j.LoggerFactory.getLogger(MainController.class);

    @GetMapping(value = {"/", "/index"})
    public String index(
            HttpServletRequest request
            , HttpServletResponse response
            , Model model
    ) {


        return "/index";
    }


    @PostMapping("/validateLogin")
    @ResponseBody
    public String validateLogin(
            HttpServletRequest request
            , HttpServletResponse response
    ) {



        return null;
    }
}

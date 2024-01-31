package org.example.user.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user") // 이 컨트롤러의 모든 요청은 /user로 시작
public class UserController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}

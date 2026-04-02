package org.core.synergy.next30.domain.profile.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.core.synergy.next30.domain.profile.entity.Member;
import org.core.synergy.next30.domain.profile.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    private final MemberRepository memberRepository;

    @PostMapping("/login")
    public RedirectView processLogin(HttpServletRequest request){

        String empId = request.getParameter("empId");
        String loginId = request.getParameter("loginId");
        String empCd = request.getParameter("empCd");
        String deptCd = request.getParameter("deptCd");

        // 방어
        if(loginId == null || loginId.isEmpty()) {
            log.error("실패: 전달된 loginId가 없습니다.");
            return new RedirectView("/api/auth/error-page");
        }



    }
    // 리다이렉트 도착지
    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String name = (session != null) ? (String) session.getAttribute("LOGIN_NAME") : "손";
        return name + "님, 환영합니다!";
    }

}

package com.zerobase.cms.user.controller;

import com.zerobase.cms.user.application.SignInApplication;
import com.zerobase.cms.user.domain.SignInForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signin")
@RequiredArgsConstructor
public class SignInController { // 토큰을 발행해 실제로 접근할 수 있는 근거 생성

    private final SignInApplication signInApplication;

    @PostMapping("/customer")
     public ResponseEntity<String> customerSignIn(@RequestBody SignInForm form) {
        return ResponseEntity.ok(signInApplication.customerLoginToken(form));
    }

    @PostMapping("/seller")
     public ResponseEntity<String> sellerSignIn(@RequestBody SignInForm form) {
        return ResponseEntity.ok(signInApplication.sellerLoginToken(form));
    }
}

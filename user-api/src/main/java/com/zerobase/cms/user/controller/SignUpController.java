package com.zerobase.cms.user.controller;

import com.zerobase.cms.user.application.SignUpApplication;
import com.zerobase.cms.user.domain.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
// 구매자 회원가입과 셀러 회원가입이 다르지 않을 것 같아 공통처리(재활용)를 위해
// /customer, /seller 가 아닌 /signup 사용.
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpApplication signUpApplication;

    /** 고객(구매자) 회원가입 api */
    @PostMapping
    public ResponseEntity<String> customerSignUp(@RequestBody SignUpForm form) {
        return ResponseEntity.ok(signUpApplication.customerSignUp(form));
    }

    // id 노출보다는 email 노출이 나을 것 같아 email을 인자로 받음.
    // 그렇지않고 암호화를 통해 더 안전하게 사용하는 방법도 있음.
    @PutMapping("/verify/customer")
    public ResponseEntity<String> verifyCustomer(String email, String code) {
        signUpApplication.customerVerify(email, code);
        return ResponseEntity.ok("인증이 완료되었습니다.");
    }
}

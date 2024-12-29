package com.zerobase.cms.user.application;

import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.domain.domain.common.UserType;
import com.zerobase.cms.user.domain.SignInForm;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.zerobase.cms.user.exception.ErrorCode.LOGIN_CHECK_FAIL;

@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final CustomerService customerService;
    private final JwtAuthenticationProvider provider;

    public String customerLoginToken(SignInForm form) {
        // 1. 로그인 가능 여부 확인
        Customer customer = customerService.findValidCustomer(form.getEmail(),
                                                              form.getPassword())
                        .orElseThrow(() -> new CustomException(LOGIN_CHECK_FAIL));

        // 2. 토큰 발행 : 아이디나 패스워드같은 정보를 그대로 노출하는 것은 보안에 취약하다.
        //               So, 일반적으로 토큰을 이용한다.
        // 3. 토큰 response
        return provider.createToken(customer.getEmail(),
                                    customer.getId(),
                                    UserType.CUSTOMER);
    }
}

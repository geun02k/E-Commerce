package com.zerobase.cms.user.service;

import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.service.customer.SignUpCustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class SignUpCustomerServiceTest {

    @Autowired
    private SignUpCustomerService signUpCustomerService;

    @Test
    void signUp() {
        //given
        SignUpForm form = SignUpForm.builder()
                .email("abc@gmail.com")
                .name("name")
                .password("123456789")
                .birth(LocalDate.now())
                .phone("01011112222")
                .build();

        //when
        Customer customer = signUpCustomerService.singUp(form);

        //then
        Assertions.assertNotNull(customer.getId()); // 정상저장되어 아이디생성 확인
        Assertions.assertNotNull(customer.getCreatedAt()); // 값 자동주입 확인
    }
}
package com.zerobase.cms.user.service;

import com.zerobase.cms.user.client.MailgunClient;
import com.zerobase.cms.user.client.mailgun.SendMailForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// 메일발송서비스
@Service
@RequiredArgsConstructor
public class EmailSendService {

    private final MailgunClient mailgunClient;

    // Feign의 response와 차이가 있어서 Response 객체 대신 String으로 반환
    public String sendEmail() {
        SendMailForm form = SendMailForm.builder()
                .from("E-Commerce project <USER@sandboxba06830e5dca4618a5bd5a9a9248ba57.mailgun.org>")
                .to("glyceria622@naver.com")
                .subject("mailgun 이메일 발송 서비스 테스트합니다.")
                .text("Test email from zerobase.")
                .build();
        return mailgunClient.sendEmail(form).getBody();
    }
}

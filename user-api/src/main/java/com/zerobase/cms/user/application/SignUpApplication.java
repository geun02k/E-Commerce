package com.zerobase.cms.user.application;

import com.zerobase.cms.user.client.MailgunClient;
import com.zerobase.cms.user.client.mailgun.SendMailForm;
import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.domain.model.Seller;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.service.customer.SignUpCustomerService;
import com.zerobase.cms.user.service.seller.SignUpSellerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.zerobase.cms.user.exception.ErrorCode.ALREADY_REGISTER_USER;

@Service
@RequiredArgsConstructor
public class SignUpApplication {

    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;
    private final SignUpSellerService signUpSellerService;

    public void customerVerify(String email, String code) {
        signUpCustomerService.verifyEmail(email, code);
    }

    public void sellerVerify(String email, String code) {
        signUpSellerService.verifyEmail(email, code);
    }

    public String customerSignUp(SignUpForm form) {
        if(signUpCustomerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ALREADY_REGISTER_USER);
        } else {
            // 구매자 회원가입
            Customer c = signUpCustomerService.singUp(form);

            // 인증 랜덤코드 생성
            String code = getRandomCode();

            // 인증메일 발송
            SendMailForm emailForm = SendMailForm.builder()
                    .from("test@dannymytester.com")
                    .to(c.getEmail())
                    .subject("Verification Email!")
                    .text(getVerificationEmailBody(c.getEmail(), c.getName(), "customer", code))
                    .build();
            ResponseEntity<String> emailBody = mailgunClient.sendEmail(emailForm);

            // 인증만료일시 및 인증코드 DB update
            signUpCustomerService.changeCustomerValidateEmail(c.getId(), code);

            return "회원가입에 성공하였습니다.";
        }
    }

    public String sellerSignUp(SignUpForm form) {
        if(signUpSellerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ALREADY_REGISTER_USER);
        } else {
            // 구매자 회원가입
            Seller s = signUpSellerService.singUp(form);

            // 인증 랜덤코드 생성
            String code = getRandomCode();

            // 인증메일 발송
            SendMailForm emailForm = SendMailForm.builder()
                    .from("test@dannymytester.com")
                    .to(s.getEmail())
                    .subject("Verification Email!")
                    .text(getVerificationEmailBody(s.getEmail(), s.getName(), "seller", code))
                    .build();
            ResponseEntity<String> emailBody = mailgunClient.sendEmail(emailForm);

            // 인증만료일시 및 인증코드 DB update
            signUpSellerService.changeSellerValidateEmail(s.getId(), code);

            return "회원가입에 성공하였습니다.";
        }
    }

    // 인증코드 생성
    private String getRandomCode() {
        return RandomStringUtils.random(10, true, true);
    }

    // 인증 이메일 템플릿 생성
    private String getVerificationEmailBody(String email, String name, String type, String code) {
        StringBuilder builder = new StringBuilder();
        // 이메일 클릭을 통한 인증
        return builder.append("Helo ").append("! Please Click Link for verification.\n\n")
                .append("http://localhost:8081/signup/" + type + "/verify?")
                .append("email=").append(email)
                .append("&code=").append(code).toString();
        // 고객에 대한 인증 endpoint(경로) : /signup/verify/customer
        // 셀러에 대한 인증 endpoint(경로) : /signup/verify/seller
    }

}

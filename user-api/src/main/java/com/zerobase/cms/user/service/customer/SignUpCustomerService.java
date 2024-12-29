package com.zerobase.cms.user.service.customer;

import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import static com.zerobase.cms.user.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class SignUpCustomerService {

    private final CustomerRepository customerRepository;

    /** 고객(구매자) 회원가입 */
    public Customer singUp(SignUpForm form) {
        return customerRepository.save(Customer.from(form));
    }

    /** email validation check
     * - 이메일 존재여부 확인 */
    public boolean isEmailExist(String email) {
        // email.toLowerCase(Locale.ROOT) : 이메일 소문자로 변경
        return customerRepository.findByEmail(email.toLowerCase(Locale.ROOT))
                .isPresent();
    }

    // @Transactional을 걸게되면 JPA 내부에서 동작하는 것 때문에 실제로 save() 메서드를 호출하지 않아도
    // 변경사항이 있으면 자연스럽게 저장을하게된다.
    // verify (인증여부) 변경되면 자동으로 DB에 저장.
    /** email validation check
     * - 이메일 인증 유효성 확인 */
    @Transactional
    public void verifyEmail(String email, String code) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        // 이미 인증이 완료된 경우
        if(customer.isVerify()) {
            throw new CustomException(ALREADY_VERIFY);

            // 인증코드 미존재 또는 서로 다른 경우
        } else if(customer.getVerificationCode().isEmpty()
            || !customer.getVerificationCode().equals(code)) {
            throw new CustomException(WRONG_VERIFICATION);

            // 인증시간이 만료된 경우
        } else if(customer.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(EXPIRE_CODE);
        }

        customer.setVerify(true);
    }

    // 이거 진짜인지 검증필요.
    // @Transactional을 걸게되면 JPA 내부에서 동작하는 것 때문에 실제로 save() 메서드를 호출하지 않아도
    // 변경사항이 있으면 자연스럽게 저장을하게된다.(?)
    /** 고객(구매자) 이메일인증관련 컬럼 update */
    @Transactional
    public LocalDateTime changeCustomerValidateEmail(Long customerId, String verificationCode) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if(customerOptional.isEmpty()){
            throw new CustomException(NOT_FOUND_USER);
        }

        Customer customer = customerOptional.get();
        customer.setVerificationCode(verificationCode);
        customer.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
        // 이메일 인증 만료일시 반환
        return customer.getVerifyExpiredAt();
    }
}

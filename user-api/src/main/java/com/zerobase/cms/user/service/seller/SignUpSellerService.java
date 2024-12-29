package com.zerobase.cms.user.service.seller;

import com.zerobase.cms.user.domain.SignUpForm;
import com.zerobase.cms.user.domain.model.Seller;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import static com.zerobase.cms.user.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class SignUpSellerService {

    private final SellerRepository sellerRepository;

    /** 셀러 회원가입 */
    public Seller singUp(SignUpForm form) {
        return sellerRepository.save(Seller.from(form));
    }

    /** email validation check
     * - 이메일 존재여부 확인 */
    public boolean isEmailExist(String email) {
        return sellerRepository.findByEmail(email.toLowerCase(Locale.ROOT))
                .isPresent();
    }

    // @Transactional을 사용 시 변경사항 자동으로 DB에 저장.
    /** email validation check
     * - 이메일 인증 유효성 확인 */
    @Transactional
    public void verifyEmail(String email, String code) {
        Seller seller = sellerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        // 이미 인증이 완료된 경우
        if(seller.isVerify()) {
            throw new CustomException(ALREADY_VERIFY);

            // 인증코드 미존재 또는 서로 다른 경우
        } else if(seller.getVerificationCode().isEmpty()
            || !seller.getVerificationCode().equals(code)) {
            throw new CustomException(WRONG_VERIFICATION);

            // 인증시간이 만료된 경우
        } else if(seller.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(EXPIRE_CODE);
        }

        seller.setVerify(true);
    }

    // @Transactional을 사용 시 변경사항 자동으로 DB에 저장.
    /** 셀러 이메일인증관련 컬럼 update */
    @Transactional
    public LocalDateTime changeSellerValidateEmail(Long sellerId, String verificationCode) {
        Optional<Seller> sellerOptional = sellerRepository.findById(sellerId);

        if(sellerOptional.isEmpty()){
            throw new CustomException(NOT_FOUND_USER);
        }

        Seller seller = sellerOptional.get();
        seller.setVerificationCode(verificationCode);
        seller.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
        // 이메일 인증 만료일시 반환
        return seller.getVerifyExpiredAt();
    }
}

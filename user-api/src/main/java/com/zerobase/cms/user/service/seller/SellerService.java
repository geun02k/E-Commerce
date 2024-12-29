package com.zerobase.cms.user.service.seller;

import com.zerobase.cms.user.domain.model.Seller;
import com.zerobase.cms.user.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    /** id와 일치하는 셀러정보 조회 */
    public Optional<Seller> findByIdAndEmail(Long id, String email) {
        return sellerRepository.findByIdAndEmail(id, email);
    }

    /** 사용자 id, password와 일치하는 셀러정보 조회 */
    public Optional<Seller> findValidSeller(String email, String password) {
        return sellerRepository.findByEmailAndPasswordAndVerifyIsTrue(email, password);
    }
}

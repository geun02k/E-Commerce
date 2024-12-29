package com.zerobase.cms.user.repository;

import com.zerobase.cms.user.domain.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    // email과 일치하는 셀러정보 조회
    Optional<Seller> findByEmail(String email);

    // id, email과 일치하는 셀러정보 조회
    Optional<Seller> findByIdAndEmail(Long id, String email);

    // email, 비밀번호가 일치 데이터 중 인증완료된 셀러정보 조회
    Optional<Seller> findByEmailAndPasswordAndVerifyIsTrue(String email, String password);
}

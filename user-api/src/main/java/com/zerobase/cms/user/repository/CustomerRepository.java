package com.zerobase.cms.user.repository;

import com.zerobase.cms.user.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // email과 일치하는 회원정보 조회
    Optional<Customer> findByEmail(String email);

    // id, email과 일치하는 회원정보 조회
    Optional<Customer> findByIdAndEmail(Long id, String email);
}

package com.zerobase.cms.user.service.customer;

import com.zerobase.cms.user.domain.model.Customer;
import com.zerobase.cms.user.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    /** id와 일치하는 회원정보 조회 */
    public Optional<Customer> findByIdAndEmail(Long id, String email) {
        return customerRepository.findByIdAndEmail(id, email);
    }

    /** 사용자 id, password와 일치하는 회원정보 조회 */
    public Optional<Customer> findValidCustomer(String email, String password) {
        return customerRepository.findByEmail(email).stream()
                .filter(customer -> customer.getPassword().equals(password)
                                                && customer.isVerify())
                .findFirst();
    }
}

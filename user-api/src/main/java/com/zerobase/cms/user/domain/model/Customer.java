package com.zerobase.cms.user.domain.model;

import com.zerobase.cms.user.domain.SignUpForm;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// BaseEntity의 createAt, modifiedAt 값 자동주입을 위한 어노테이션
@AuditOverride(forClass = BaseEntity.class)
public class Customer extends BaseEntity {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // 이메일중복불가
    private String email;
    private String name;
    private String password;
    private String phone;
    private LocalDate birth;

    // 이메일 인증관련 변수 (인증 테이블을 별도로 둘 수 있지만 고객정보와 통합.)
    private LocalDateTime verifyExpiredAt; // 인증만료시간(하나의 이메일로 무한인증 못하도록)
    private String verificationCode; // 코드기반 인증을 위한 코드 저장 변수
    private boolean verify; // 인증여부

    // SignUpForm에 회원정보 담아서 회원가입.
    // : SignUpForm -> Customer 로 변환메서드
    public static Customer from(SignUpForm form) {
        return Customer.builder()
                .email(form.getEmail().toLowerCase(Locale.ROOT)) // unique 체크를 위해 소문자로 변경
                .name(form.getName())
                .password(form.getPassword())
                .phone(form.getPhone())
                .birth(form.getBirth())
                .verify(false)
                .build();
    }
}

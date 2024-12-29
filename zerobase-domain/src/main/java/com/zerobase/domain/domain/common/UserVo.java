package com.zerobase.domain.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 로그인 대상자가 어떤 객체이든 관계없이(Customer 이든 Seller 이든 관계없이)
// 로그인 시 id, email 2개를 기반으로 동작시키기 위해 UserInterface 사용.
@Getter
@AllArgsConstructor
public class UserVo {

    private Long id;
    private String email;

}

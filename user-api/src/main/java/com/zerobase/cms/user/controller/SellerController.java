package com.zerobase.cms.user.controller;

import com.zerobase.cms.user.domain.model.Seller;
import com.zerobase.cms.user.domain.seller.SellerDto;
import com.zerobase.cms.user.exception.CustomException;
import com.zerobase.cms.user.service.seller.SellerService;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.domain.domain.common.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.zerobase.cms.user.exception.ErrorCode.NOT_FOUND_USER;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {

    private final JwtAuthenticationProvider provider;
    private final SellerService sellerService;

    /** 회원정보 조회 */
    @GetMapping("/getInfo")
    public ResponseEntity<SellerDto> getInfo(@RequestHeader(name="X-AUTH-TOKEN") String token) {
        // 토큰에서 회원 id, email 얻기
        UserVo userVo = provider.getUserVo(token);

        // 회원 id, email에 따른 회원정보 조회
        // 필터에서 이미 회원가입 여부에 대해 조회하기 때문에 불필요한 코드일 수 있다.
        Seller seller = sellerService.findByIdAndEmail(userVo.getId(), userVo.getEmail())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        return ResponseEntity.ok(SellerDto.from(seller));
    }
}

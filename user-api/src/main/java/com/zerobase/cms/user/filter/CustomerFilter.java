package com.zerobase.cms.user.filter;

import com.zerobase.cms.user.service.customer.CustomerService;
import com.zerobase.domain.config.JwtAuthenticationProvider;
import com.zerobase.domain.domain.common.UserVo;
import lombok.RequiredArgsConstructor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// 실 서비스에서는 해당 필터처럼은 하기는 어렵다.

// 고객관련 기능은 토큰없이 접근불가
// = /customer/ 로 시작하는 url 패턴에 대해 url 접근 전 필터 적용
@WebFilter(urlPatterns = "/customer/*")
@RequiredArgsConstructor
public class CustomerFilter implements Filter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final CustomerService customerService;

    /** 사용자 회원가입여부, 토큰 유효성 확인 */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("X-AUTH-TOKEN"); // JWT토큰 데이터를 가지는 헤더

        // 토큰 유효성 확인
        if(!jwtAuthenticationProvider.validateToken(token)) {
            throw new ServletException("Invalid Access.");
        }

        // 사용자 회원가입여부 확인.
        UserVo vo = jwtAuthenticationProvider.getUserVo(token);
        customerService.findByIdAndEmail(vo.getId(), vo.getEmail())
                .orElseThrow(() -> new ServletException("Invalid Access."));

        // 다음 필터 호출
        filterChain.doFilter(request, response);
    }
}

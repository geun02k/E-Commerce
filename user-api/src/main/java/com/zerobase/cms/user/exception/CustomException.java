package com.zerobase.cms.user.exception;

import lombok.Setter;

@Setter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        // 부모인 RuntimeException에 에러메시지를 인자로 전달해 부모 생성자 생성
        super(errorCode.getDetail());
        this.errorCode = errorCode;
    }
}

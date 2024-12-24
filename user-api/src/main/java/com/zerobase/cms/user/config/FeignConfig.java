package com.zerobase.cms.user.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
//   vm 옵션을 사용하기 (git commit 시 비밀키 노출되지 않음)
//   - run버튼 > Edit Configurations > Build and run의 2번째 칸에 -D 옵션을 사용해 프로퍼티 네임에 값을 입력해 주입 가능하도록함.
//     ex) -Dmailgun.key=key-비밀키
    @Value(value = "${mailgun.key}")
    private String mailgunKey;

    @Qualifier(value = "mailgun")
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("api", mailgunKey);
    }
}

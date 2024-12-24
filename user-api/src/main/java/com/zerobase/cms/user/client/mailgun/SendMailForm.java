package com.zerobase.cms.user.client.mailgun;

import lombok.*;

// 메일 발송을 위한 form (model)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMailForm {
    private String from;
    private String to;
    private String subject;
    private String text;
}

package com.zerobase.cms.user.client;

import com.zerobase.cms.user.client.mailgun.SendMailForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

// Mailgun 메일전송 api 호출하기
// Mailgun에서 Send > Overview > API [Select] 버튼 클릭 > Java 클릭
// Java로 작성된 api 호출 샘플코드 확인가능.
/**
 * import java.io.File;
 *   import com.mashape.unirest.http.HttpResponse;
 *   import com.mashape.unirest.http.JsonNode;
 *   import com.mashape.unirest.http.Unirest;
 *   import com.mashape.unirest.http.exceptions.UnirestException;
 *   public class MGSample {
 *   	 // ...
 *   	public static JsonNode sendSimpleMessage() throws UnirestException {
 *   		HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + sandboxba06830e5dca4618a5bd5a9a9248ba57.mailgun.org + "/messages"),
 *   			.basicAuth("api", API_KEY)
 *   			.queryString("from", "Excited User <USER@sandboxba06830e5dca4618a5bd5a9a9248ba57.mailgun.org>")
 *   			.queryString("to", "artemis@example.com")
 *   			.queryString("subject", "hello")
 *   			.queryString("text", "testing")
 *   			.asJson();
 *   		return request.getBody();
 *   	    }
 *   }
 */
// API 키 얻기
// Mailgun에서 우측상단의 사용자명의 토글클릭 > API Security > [create API key] 클릭

// url : mailgun의 메일 발송 api의 base url
@FeignClient(name="mailgun", url="https://api.mailgun.net/v3/")
// 여러개의 feignClient 사용할 예정이기에 Qualifier 로 구별.
@Qualifier("mailgun")
public interface MailgunClient {

    /** 메일발송 */
    @PostMapping("sandboxba06830e5dca4618a5bd5a9a9248ba57.mailgun.org/messages")
    // 샘플코드에서 일반 requestbody로 전송하지 않고 queryString으로 보냈기에
    // StringQueryMap을 통해 데이터를 전달한다.
    ResponseEntity<String> sendEmail(@SpringQueryMap SendMailForm form);
}

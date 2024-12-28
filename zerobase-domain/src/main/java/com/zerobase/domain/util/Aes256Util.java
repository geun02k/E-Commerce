package com.zerobase.domain.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class Aes256Util { // 암호화방식지정 : 아무것도 안알려주고 그냥 찾아보라는데...

    // AES/CBC/PKC55Padding 로 오타 발생 시 아래의 에러 발생.
    // java.security.NoSuchAlgorithmException: Cannot find any provider supporting AES/CBC/PKCS5Padding 스프링부트 2.x 버전
    // 자바에서 해당 암호화알고리즘을 지원하지 않기 때문이라고 한다.
    public static String alg = "AES/CBC/PKCS5Padding";
    private static final String KEY = "ZEROBASEKEYISZEROBASEKEY";
    private static final String IV = KEY.substring(0,16);

    // 암호화
    public static String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);

            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64String(encrypted);

        } catch (Exception e) {
            return null;
        }
    }

    // 복호화
    public static String decrypt(String cipherText) {
        try {
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

            byte[] decodeBytes = Base64.decodeBase64(cipherText);
            byte[] decrypted = cipher.doFinal(decodeBytes);
            return new String(decrypted, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

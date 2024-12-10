package com.everyonewaiter.security;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
class SignatureEncoder implements Encoder<SecureEncodable, Decodable> {

	private static final String HMAC_SHA256 = "HmacSHA256";

	@Override
	public String encode(SecureEncodable encodable) {
		try {
			Mac hmac = Mac.getInstance(HMAC_SHA256);
			hmac.init(new SecretKeySpec(encodable.getSecretKey().getBytes(StandardCharsets.UTF_8), HMAC_SHA256));
			byte[] rawHmac = hmac.doFinal(encodable.getRawText().getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(rawHmac);
		} catch (NoSuchAlgorithmException | InvalidKeyException exception) {
			throw new IllegalStateException("fail.generate.signature", exception);
		}
	}

	@Override
	public boolean matches(SecureEncodable encodable, Decodable decodable) {
		return decodable.getEncodedText().equals(encode(encodable));
	}
}

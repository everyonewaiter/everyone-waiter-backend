package com.everyonewaiter.message.adapter.out.infrastructure;

import java.io.IOException;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.everyonewaiter.security.SecureEncodable;
import com.everyonewaiter.security.SignatureEncoder;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class NaverSensClient {

	private static final String BASE_URL = "https://sens.apigw.ntruss.com";
	private static final String TIMESTAMP_HEADER = "x-ncp-apigw-timestamp";
	private static final String ACCESS_KEY_HEADER = "x-ncp-iam-access-key";
	private static final String SIGNATURE_HEADER = "x-ncp-apigw-signature-v2";

	private final NaverSensProperties naverSensProperties;
	private final SignatureEncoder signatureEncoder;

	public RestClient initialize(HttpMethod method, String url) {
		String accessKey = naverSensProperties.getAccessKey();
		String secretKey = naverSensProperties.getSecretKey();
		NaverSensSignature naverSensSignature = new NaverSensSignature(accessKey, secretKey, method, url);

		return RestClient.builder()
			.baseUrl(BASE_URL)
			.defaultHeaders(httpHeaders -> {
				httpHeaders.setContentType(MediaType.APPLICATION_JSON);
				httpHeaders.add(TIMESTAMP_HEADER, String.valueOf(System.currentTimeMillis()));
				httpHeaders.add(ACCESS_KEY_HEADER, accessKey);
				httpHeaders.add(SIGNATURE_HEADER, signatureEncoder.encode(naverSensSignature));
			})
			.defaultStatusHandler(HttpStatusCode::is4xxClientError, this::handleClientError)
			.defaultStatusHandler(HttpStatusCode::is5xxServerError, this::handleServerError)
			.build();
	}

	private void handleClientError(HttpRequest request, ClientHttpResponse response) throws IOException {
		throw new IllegalArgumentException("[%s] 잘못된 알림톡 전송 요청입니다.".formatted(response.getStatusText()));
	}

	private void handleServerError(HttpRequest request, ClientHttpResponse response) throws IOException {
		throw new IllegalStateException("[%s] 네이버 SENS 시스템 문제로 인해 알림톡 전송에 실패했습니다.".formatted(response.getStatusText()));
	}

	static class NaverSensSignature implements SecureEncodable {

		private final String accessKey;
		private final String secretKey;
		private final HttpMethod method;
		private final String url;

		public NaverSensSignature(String accessKey, String secretKey, HttpMethod method, String url) {
			this.accessKey = accessKey;
			this.secretKey = secretKey;
			this.method = method;
			this.url = url;
		}

		@Override
		public String getSecretKey() {
			return secretKey;
		}

		@Override
		public String getRawText() {
			return """
				%s %s
				%s
				%s
				""".formatted(method.name(), url, System.currentTimeMillis(), accessKey).trim();
		}
	}
}

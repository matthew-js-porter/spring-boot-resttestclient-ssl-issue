package com.example.spring_boot_resttestclient_ssl;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class HelloControllerRestTestClientTests {

	@Autowired
	private RestTestClient restTestClient;

	@Test
	void helloOverSsl() {
		this.restTestClient.get()
			.uri("/hello")
			.exchange()
			.expectStatus().isOk()
			.expectBody(String.class).isEqualTo("Hello, World!");
	}

}

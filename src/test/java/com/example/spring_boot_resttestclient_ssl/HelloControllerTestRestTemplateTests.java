package com.example.spring_boot_resttestclient_ssl;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class HelloControllerTestRestTemplateTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void helloOverSsl() {
		assertThat(this.restTemplate.getRootUri()).startsWith("https://");
		ResponseEntity<String> response = this.restTemplate.getForEntity("/hello", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo("Hello, World!");
	}

}

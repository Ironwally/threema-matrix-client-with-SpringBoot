package com.hka.intranet.messageprotocol.matrix.springboot.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void mainStartsWithoutError() {
		DemoApplication.main(new String[]{});
		org.assertj.core.api.Assertions.assertThat(DemoApplication.class).isNotNull();
	}

}

package com.example.tobyspring;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TobyspringApplicationTests {

	public static void main(String[] args) {
		JUnitCore.main("com.example.tobyspring.user.UserDaoTest");
	}

	@Test
	void contextLoads() {
	}

}

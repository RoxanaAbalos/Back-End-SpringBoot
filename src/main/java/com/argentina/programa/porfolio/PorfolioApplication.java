package com.argentina.programa.porfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PorfolioApplication implements CommandLineRunner{
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(PorfolioApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		String password = "noelia1234";

		for(int i=0; i < 4 ;i++) {
			String passwordBCrypt = passwordEncoder.encode(password);
			System.out.println(passwordBCrypt);
		}

	}
}

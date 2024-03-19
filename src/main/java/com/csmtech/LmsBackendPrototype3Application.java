package com.csmtech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.csmtech.entity.UserMaster;
import com.csmtech.repository.UserMasterRepository;

@SpringBootApplication
public class LmsBackendPrototype3Application implements CommandLineRunner {
	@Autowired
	private UserMasterRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(LmsBackendPrototype3Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		UserMaster uu = repo.findById(1).get();
		uu.setPassword(new BCryptPasswordEncoder().encode("admin"));
		repo.save(uu);
	}

}

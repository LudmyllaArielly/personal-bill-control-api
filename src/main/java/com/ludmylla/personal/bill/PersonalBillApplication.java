package com.ludmylla.personal.bill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class PersonalBillApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalBillApplication.class, args);
	}

}

package com.konex.servapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.konex.servapp.entity.User;
import com.konex.servapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Konex24Application {

	private static final Logger log = LoggerFactory.getLogger(Konex24Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Konex24Application.class, args);
		System.err.println("APPLICATION STARTED =======================================================================");
	}

//		@Bean
//		public CommandLineRunner demo(UserRepository repository) {
//			return (args) -> {
//				// save a couple of customers
//				repository.save(new User("Jaba", "Lysa"));
//				repository.save(new User("Dohla", "Krysa"));
//
//				// fetch all customers
//				log.info("Customers found with findAll():");
//				log.info("-------------------------------");
//				for (User customer : repository.findAll()) {
//					log.error(customer.toString());
//				}
//				log.info("");
//
//				// fetch an individual customer by ID
//				User customer = repository.findOne(9L);
//				log.info("Customer found with ID #9 - findOne(9L):");
//				log.info("--------------------------------");
//				log.error(customer.toString());
//				log.info("");
//
//				// fetch customers by last name
//				log.info("Customer found with findByUsername('DimaJaba'):");
//				log.info("--------------------------------------------");
//				for (User dima : repository.findByUsername("DimaJaba")) {
//					log.error(dima.toString());
//				}
//				log.info("");
//			};
//		}


}


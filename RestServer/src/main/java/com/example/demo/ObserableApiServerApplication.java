package com.example.demo;

import java.util.Collection;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@SpringBootApplication
public class ObserableApiServerApplication {
	
	@Autowired
	private CustomerRepository cr;
	
	@Autowired
	private ProductRepository pr;
	
	@Bean
	CommandLineRunner runner(CustomerRepository cr,ProductRepository pr) {
		return args -> {
			Stream.of("Saurabh:Agarwal", "Vasu:Tyagi", "Shariq:Khan", "Yash:Garg").forEach(name -> cr.save(new Customer(name.split(":")[0],name.split(":")[1])));
			cr.findAll().forEach(System.out::println);
			Stream.of("Coke", "Pepsi", "7up", "Slice").forEach(name -> pr.save(new Product(name)));
			pr.findAll().forEach(System.out::println);
		};
	}
	public static void main(String[] args) {
		SpringApplication.run(ObserableApiServerApplication.class, args);
	}
}
@RepositoryRestResource
interface CustomerRepository extends JpaRepository<Customer, Long> {
	@RestResource(path = "by-surname")
	Collection<Customer> findBySurname(@Param("rn") String rn);
}
@RepositoryRestResource
interface ProductRepository extends JpaRepository<Product, Long> {
	@RestResource(path = "by-desc")
	Collection<Customer> findByDescription(@Param("rn") String rn);
}
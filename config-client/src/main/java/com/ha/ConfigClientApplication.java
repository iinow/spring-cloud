package com.ha;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ConfigClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigClientApplication.class, args);
	}
}

@RestController
@RefreshScope
class CLientBean {
	@Value("${ha.name}")
	private String name;
	
	@PostConstruct
	public void test() {
		System.out.println(name);
	}
	
	@GetMapping("/test")
	public String dd() {
		return name;
	}
}

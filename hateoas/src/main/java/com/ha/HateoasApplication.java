package com.ha;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.Serializable;
import java.util.Calendar;
import java.util.concurrent.atomic.LongAdder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ha.mq.Sender;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class HateoasApplication implements CommandLineRunner {

	@Autowired
	Sender sender;
	
	public static void main(String[] args) {
		SpringApplication.run(HateoasApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		sender.send("Hello message??");
	}
}

@RestController
class HateoasController {
	
	TPSHealth health = new TPSHealth();
	
	@CrossOrigin(origins = {""})
	@GetMapping(path = "/greeting")
	@ResponseBody
	public ResponseEntity<Greet> greeting(
			@RequestParam(name = "name", required = false, defaultValue = "HATEOAS") String name) {
		Greet greet = new Greet(name);
		greet.add(linkTo(methodOn(HateoasController.class)
						.greeting(name))
					.withRel(Link.REL_SELF));//withSelfRel()
		
		return ResponseEntity.ok(greet);
	}
	
	@GetMapping(path = "/greeting2")
	Mono<Greet> greet() {
		System.out.println("Serving Request!!!");
		health.updateTx();
		return Mono.just(new Greet("ÈÄ¿¨"));
	}
}

class Greet extends ResourceSupport implements Serializable {
	private static final long serialVersionUID = -6086561125734144824L;
	
	private String name;
	
	public Greet(String name) {
		this.name = name;
	}
	
	public String getMessage() {
		return this.name;
	}
}

class TPSCounter {
	LongAdder count;
	int threshold = 2;
	Calendar expiry = null;
	
	TPSCounter() {
		this.count = new LongAdder();
		this.expiry = Calendar.getInstance();
		this.expiry.add(Calendar.MINUTE, 1);
	}
	
	boolean isExpired() {
		return Calendar.getInstance().after(expiry);
	}
	
	boolean isWeak() {
		return (count.intValue() > threshold);
	}
	
	void increment() {
		count.increment();
	}
}

class TPSHealth implements HealthIndicator {
	TPSCounter counter;
	
	@Override
	public Health health() {
		boolean health = counter.isWeak();
		
		if(health) {
			return Health.outOfService()
					.withDetail("Too many requests", "OutofService")
					.build();
		}
		return Health.up().build();
	}
	
	void updateTx() {
		if(counter == null || counter.isExpired()) {
			counter = new TPSCounter();
		}
		counter.increment();
	}
}
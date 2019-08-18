package com.ha.mq;

//import org.springframework.amqp.rabbit.AsyncRabbitTemplate.RabbitMessageFuture;
//import org.springframework.amqp.rabbit.core.RabbitMessageOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//import org.springframework.amqp.core.Queue;

@Component
public class Sender {

//	@Autowired
//	RabbitMessageOperations oper;
	
//	@Bean
//	Queue queue() {
//		return new Queue("TestQ", false);
//	}
	
	public void send(String message) {
//		oper.convertAndSend("TestQ", message);
	}
}

package com.spring.designpatterns.observer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.designpatterns.observer.publisher.CustomEventPublisher;

public class SpringObserverDesignPatternDemo {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");
		CustomEventPublisher customEventPublisher = applicationContext.getBean(CustomEventPublisher.class);
		customEventPublisher.publish();
		((ClassPathXmlApplicationContext)applicationContext).close();
	}

}

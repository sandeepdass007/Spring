package com.spring.designpatterns.observer.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.spring.designpatterns.observer.event.CustomEvent;

@Component
public class CustomListener implements ApplicationListener<CustomEvent> {

	@Override
	public void onApplicationEvent(CustomEvent customEvent) {
		System.out.println("Invoked listener");
	}

}

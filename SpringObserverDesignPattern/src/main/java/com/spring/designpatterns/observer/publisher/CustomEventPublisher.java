package com.spring.designpatterns.observer.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import com.spring.designpatterns.observer.event.CustomEvent;

@Component
public class CustomEventPublisher implements ApplicationEventPublisherAware {
	ApplicationEventPublisher applicationEventPublisher;

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	public void publish() {
		CustomEvent customEvent = new CustomEvent(this);
		applicationEventPublisher.publishEvent(customEvent);
	}
}

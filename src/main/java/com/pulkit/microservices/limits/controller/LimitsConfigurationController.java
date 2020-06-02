package com.pulkit.microservices.limits.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.pulkit.microservices.limits.bean.LimitConfiguration;
import com.pulkit.microservices.limits.service.Configuration;

@RestController
@RequestMapping("service")
public class LimitsConfigurationController {
	
	@Autowired
	private Configuration configuration;
	
	@RequestMapping(value="limits",method = RequestMethod.GET)
	public LimitConfiguration retrieveLimitsFomConfiguration(){
		return new LimitConfiguration(configuration.getMaximum(),configuration.getMinimum());
	}
	
	@RequestMapping(value="fault-tolerance",method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod="fallBackMethod")
	public LimitConfiguration retrieveLimits(){
		throw new RuntimeException("Exception Occured");
	}
	
	@RequestMapping(value="fault-tolerance",method = RequestMethod.GET)
	public LimitConfiguration fallBackMethod(){
		return new LimitConfiguration(9,9999);
	}

}

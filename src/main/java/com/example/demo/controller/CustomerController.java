package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.CustomerDao;
import com.example.demo.model.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class CustomerController {

	@Autowired
	CustomerDao customerDao;

	@GetMapping("")
	public String test() {
		return "test";
	}

	// Insert Activity
	@PostMapping("/customer")
	public Customer saveActivity(@RequestBody Customer customer) {
		return customerDao.save(customer);
	}

	@GetMapping("/filter")
	public List<Customer> getFilter(@RequestBody JsonNode customer) {
		String customerId = customer.get("customerId").toString();
		String fromValueStampTime = customer.get("fromValueStampTime").toString();
		String toValueStampTime = customer.get("toValueStampTime").toString();
		String activityType = customer.get("activityType").toString();
		String fromAmount = customer.get("fromAmount").toString();
		String toAmount = customer.get("toAmount").toString();
		String movement = customer.get("movement").toString();
		return customerDao.filter(removeFirstandLast(customerId), fromValueStampTime, toValueStampTime, removeFirstandLast(activityType), fromAmount, toAmount, removeFirstandLast(movement));
	}

	public static String removeFirstandLast(String str) {
		str = str.substring(1, str.length() - 1);
		return str;
	}
}

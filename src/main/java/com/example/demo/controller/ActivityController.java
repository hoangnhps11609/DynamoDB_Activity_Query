package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.ActivityDao;
import com.example.demo.model.Activity;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class ActivityController {

	@Autowired
	ActivityDao activityDao;

	@GetMapping("")
	public String test() {
		return "test";
	}

	// Insert Activity
	@PostMapping("/customer")
	public Activity saveActivity(@RequestBody Activity activity) {
		return activityDao.save(activity);
	}

	@GetMapping("/filter")
	public List<Activity> getFilter(@RequestBody JsonNode activity) {
		String customerId = activity.get("customerId").toString();
		String fromValueStampTime = activity.get("fromValueStampTime").toString();
		String toValueStampTime = activity.get("toValueStampTime").toString();
		String activityType = activity.get("activityType").toString();
		String fromAmount = activity.get("fromAmount").toString();
		String toAmount = activity.get("toAmount").toString();
		String movement = activity.get("movement").toString();
		return activityDao.filter(removeFirstandLast(customerId), fromValueStampTime, toValueStampTime, removeFirstandLast(activityType), fromAmount, toAmount, removeFirstandLast(movement));
	}

	public static String removeFirstandLast(String str) {
		str = str.substring(1, str.length() - 1);
		return str;
	}
}

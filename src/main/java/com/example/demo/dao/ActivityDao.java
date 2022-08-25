package com.example.demo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.example.demo.model.Activity;

@Repository
public class ActivityDao {

	@Autowired
	private DynamoDBMapper dynamoDBMapper;

	public Activity save(Activity customer) {
		dynamoDBMapper.save(customer);
		return customer;
	}

	public List<Activity> filter(String customerId, String fromValueStampTime, String toValueStampTime, String activityType, String fromAmount, String toAmount, String movement) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		System.out.println(customerId);
		eav.put(":v1", new AttributeValue().withS(customerId));
		eav.put(":v2", new AttributeValue().withN(fromValueStampTime));
		eav.put(":v3", new AttributeValue().withN(toValueStampTime));
		eav.put(":v4", new AttributeValue().withS(activityType));
		eav.put(":v5", new AttributeValue().withN(fromAmount));
		eav.put(":v6", new AttributeValue().withN(toAmount));
		eav.put(":v7", new AttributeValue().withS(movement));
		DynamoDBQueryExpression<Activity> queryExpression = new DynamoDBQueryExpression<Activity>()
				.withKeyConditionExpression("customerId = :v1 and valueStampTime between :v2 and :v3")
				.withFilterExpression("contains(activityType, :v4) and amount >= :v5 and amount <= :v6 and contains(movement, :v7)")
				.withExpressionAttributeValues(eav);
		List<Activity> latestReplies = dynamoDBMapper.query(Activity.class, queryExpression);
		return latestReplies;
	}

}

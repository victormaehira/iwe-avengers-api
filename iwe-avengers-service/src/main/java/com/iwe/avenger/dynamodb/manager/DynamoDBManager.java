package com.iwe.avenger.dynamodb.manager;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class DynamoDBManager {
	private static DynamoDBMapper mapper;

	static {

		final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_2).build();

		mapper = new DynamoDBMapper(ddb);
	}

	public static DynamoDBMapper mapper() {
		return DynamoDBManager.mapper;
	}
}

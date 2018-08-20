package com.iwe.avengers.dao;

import java.util.Optional;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.iwe.avenger.dynamodb.entity.Avenger;
import com.iwe.avenger.dynamodb.manager.DynamoDBManager;

public class AvengerDAO {

	private DynamoDBMapper mapper = DynamoDBManager.mapper();
	
	public Avenger find(String id) {
		final Avenger avenger = mapper.load(Avenger.class, id);
		return Optional.ofNullable(avenger).get();
	}

	public Avenger create(Avenger newAvenger) {
		mapper.save(newAvenger);
		return newAvenger;
	}

	//passar Avenger
	public void delete(String id) {
		//Avenger avenger =  mapper.get(id);
		//mapper.delete(id);
	}

	public Avenger update(Avenger avenger) {
		return null;
	}

}

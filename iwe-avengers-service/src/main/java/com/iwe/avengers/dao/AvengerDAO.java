package com.iwe.avengers.dao;

import java.util.HashMap;
import java.util.Map;

import com.iwe.avenger.dynamodb.entity.Avenger;

public class AvengerDAO {

	public Map<String, Avenger> mapper = new HashMap<>();
	
	public AvengerDAO() {
		mapper.put("sdsa-sasa-asas-sasa", new Avenger("sdsa-sasa-asas-sasa","Iron Man","Tony Stark"));
		mapper.put("aaaa-aaaa-aaaa-aaaa", new Avenger("aaaa-aaaa-aaaa-aaaa","Hulk","Bruce"));
	}

	public Avenger find(String id) {
		return mapper.get(id);
	}

	public Avenger create(Avenger newAvenger) {
		String id = mapper.size() + "";
		newAvenger.setId(id);
		mapper.put(id, newAvenger);
		return newAvenger;
	}

	public void delete(String id) {
		//Avenger avenger =  mapper.get(id);
		mapper.remove(id);
	}

	public Avenger update(Avenger avenger) {
		mapper.replace(avenger.getId(), avenger);
		return avenger;
	}

}

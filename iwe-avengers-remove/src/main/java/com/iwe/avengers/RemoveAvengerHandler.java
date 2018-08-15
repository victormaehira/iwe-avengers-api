package com.iwe.avengers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.iwe.avenger.dynamodb.entity.Avenger;
import com.iwe.avenger.lambda.exception.AvengerNotFoundException;
import com.iwe.avenger.lambda.response.HandlerResponse;
import com.iwe.avengers.dao.AvengerDAO;

public class RemoveAvengerHandler implements RequestHandler<Avenger, HandlerResponse> {

	private AvengerDAO dao = new AvengerDAO();
	
	@Override
	public HandlerResponse handleRequest(final Avenger avenger, final Context context) {
		
		final String id = avenger.getId();
		context.getLogger().log("[#] DELETE - Searching Avenger with id: " + id);
		
		final Avenger retrievedAvenger = dao.find(id);
		
		if (retrievedAvenger == null) {
			throw new AvengerNotFoundException("[NotFound] DELETE - Avenger id: " + id + " not found");
			
		}
		
		context.getLogger().log("[#] - Deleting Avenger with id: " + id);
		dao.delete(id);
		
		final HandlerResponse response = HandlerResponse.builder()
										.setStatusCode(204)
										//.setObjectBody(avenger)
										.build();
		context.getLogger().log("[#] - Avenger deleted! =)");
		return response;
	}
}

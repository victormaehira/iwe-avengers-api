package com.iwe.avengers.test.authorization;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.ChallengeNameType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.RespondToAuthChallengeRequest;
import com.amazonaws.services.cognitoidp.model.RespondToAuthChallengeResult;

public class TokenGenerator {

	public String getToken() {
		
		String authresult = null;

		final String clientId = "7oi8h72jkm7stjbkdbca5rb5h";
		final String userPoolId = "us-east-2_7R0IT5v2t";

		final AuthenticationHelper auth = new AuthenticationHelper(userPoolId, clientId);

		final InitiateAuthRequest initiateAuthRequest = new InitiateAuthRequest();
		initiateAuthRequest.setAuthFlow(AuthFlowType.USER_SRP_AUTH);
		initiateAuthRequest.setClientId(clientId);
		initiateAuthRequest.addAuthParametersEntry("USERNAME", "petgol");
		
		//O conceito de algoritmos de chave p�blica � que voc� tem duas chaves, um p�blico que est� dispon�vel para 
		//todos e um que � privado e conhecido apenas por voc�
		// nesse passo estamos passando uma chave p�blica gerada a partir de calculos e criptografia
		initiateAuthRequest.addAuthParametersEntry("SRP_A", auth.getA().toString(16));

		final AnonymousAWSCredentials awsCreds = new AnonymousAWSCredentials();
		final AWSCognitoIdentityProvider cognitoIdentityProvider = AWSCognitoIdentityProviderClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion(Regions.US_EAST_2).build();//OHIO
		
		final InitiateAuthResult initiateAuthResult = cognitoIdentityProvider.initiateAuth(initiateAuthRequest);

		if (ChallengeNameType.PASSWORD_VERIFIER.toString().equals(initiateAuthResult.getChallengeName())) {
			// Nesse passo estamos respondendo ao desafio do PASSWORD, e a senha ser� totalmente criptografada para o envio a AWS
			// Essa criptografia � realizada junto com um outro Hash SRP que � devolvido pela AWS como retorno da requisi��o
			RespondToAuthChallengeRequest challengeRequest = auth.userSrpAuthRequest(initiateAuthResult, "12345678");
			RespondToAuthChallengeResult result = cognitoIdentityProvider.respondToAuthChallenge(challengeRequest);
			authresult = result.getAuthenticationResult().getIdToken();
		}

		return authresult;
	}

}

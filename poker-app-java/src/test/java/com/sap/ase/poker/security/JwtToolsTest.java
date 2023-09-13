package com.sap.ase.poker.security;


import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JwtToolsTest {

	@Test
	public void encodeSign_decodeVerify() {
		JwtTools jwtTools = new JwtTools("test-secret");
		String signedAndEncoded = jwtTools.create("john-doe", "John Doe");
		
		DecodedJWT decoded = jwtTools.verifyAndDecode(signedAndEncoded);
		String decodedId = decoded.getClaim("user_id").asString();
		String decodedName = decoded.getClaim("user_name").asString();
		
		assertEquals("john-doe", decodedId);
		assertEquals("John Doe", decodedName);
	}
	
	@Test
	public void encodeSign_decodeVerify_differentSecret() {
		JwtTools jwtTools1 = new JwtTools("test-secret1");
		JwtTools jwtTools2 = new JwtTools("test-secret2");
		String signedAndEncoded = jwtTools1.create("john-doe", "John Doe");

		assertThrows(SignatureVerificationException.class, () -> jwtTools2.verifyAndDecode(signedAndEncoded));
	}
}

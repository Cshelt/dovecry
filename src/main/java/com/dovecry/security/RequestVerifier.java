package com.dovecry.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class RequestVerifier {
    public static final String SECRETSTRING = "SecretKeyToGenJWTs";
    public static final long EXPIRATIONTIME = 864_000_000;
    public static final String TOKENPREFIX = "Bearer ";
    public static final String HEADERSTRING = "Authorization";
	
	private Boolean verified=false;

	public Boolean verifyRequest(HttpServletRequest httpServletRequest, String username) {
		String token = httpServletRequest.getHeader(HEADERSTRING);
        if (token != null) {
            String role = Jwts.parser()
                    .setSigningKey(SECRETSTRING.getBytes())
                    .parseClaimsJws(token.replace(TOKENPREFIX, ""))
                    .getBody().get("sub").toString();
            if(role.equals(username)) verified=true;
        }
        return verified;
		
	}
}

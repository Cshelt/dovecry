package com.dovecry.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
@PropertySource("classpath:security.properties")
public class RequestVerifier {
	@Value("${jwt.secret}")
    public String SECRETSTRING;
	@Value("${jwt.tokenprefix}")
    public String TOKENPREFIX;
	@Value("${jwt.headerstring}")
    public String HEADERSTRING;
	
	private Boolean verified;

	public Boolean verifyUserForResource(HttpServletRequest httpServletRequest, String username) {
		verified=false;
		String token = httpServletRequest.getHeader(HEADERSTRING);
        if (token != null) {
            String role = Jwts.parser()
                    .setSigningKey(SECRETSTRING.getBytes())
                    .parseClaimsJws(token.replace(TOKENPREFIX, ""))
                    .getBody().get("sub").toString();
            if(role.equals(username)) {
            	verified=true;
            }
        }
        return verified;
		
	}
	public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(HttpServletRequest httpServletRequest) {
		String token = httpServletRequest.getHeader(HEADERSTRING);
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(SECRETSTRING.getBytes())
                    .parseClaimsJws(token.replace(TOKENPREFIX, ""))
                    .getBody()
                    .getSubject();
            String role = Jwts.parser()
                    .setSigningKey(SECRETSTRING.getBytes())
                    .parseClaimsJws(token.replace(TOKENPREFIX, ""))
                    .getBody().get("sub").toString();
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(role));
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
            return null;
        }
        return null;
	}
}

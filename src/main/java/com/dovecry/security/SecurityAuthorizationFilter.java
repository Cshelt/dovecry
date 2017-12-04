package com.dovecry.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class SecurityAuthorizationFilter extends BasicAuthenticationFilter{
    public static final String SECRETSTRING = "SecretKeyToGenJWTs";
    public static final long EXPIRATIONTIME = 864_000_000;
    public static final String TOKENPREFIX = "Bearer ";
    public static final String HEADERSTRING = "Authorization";
    
	public SecurityAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADERSTRING);

        if (header == null || !header.startsWith(TOKENPREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADERSTRING);
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

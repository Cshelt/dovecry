package com.dovecry.security;

import com.dovecry.graphdb.model.ModelUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@PropertySource("classpath:security.properties")
public class SecurityAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	@Value("${jwt.secret}")
    public String SECRETSTRING;
	@Value("${jwt.expiration}")
	public String EXPIRATIONTIMESTRING;
	@Value("${jwt.tokenprefix}")
    public String TOKENPREFIX;
	@Value("${jwt.headerstring}")
    public String HEADERSTRING;

    private AuthenticationManager authenticationManager;

    public SecurityAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            ModelUser modelUser = new ObjectMapper().readValue(req.getInputStream(),ModelUser.class);
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(modelUser.getRole()));
            Authentication authentication = 
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            modelUser.getUsername(),
                            modelUser.getPassword(),
                            authorities));
                    return authentication;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(EXPIRATIONTIMESTRING)))
                .signWith(SignatureAlgorithm.HS512, SECRETSTRING.getBytes())
                .compact();
        res.addHeader(HEADERSTRING, TOKENPREFIX + token);
    }
}
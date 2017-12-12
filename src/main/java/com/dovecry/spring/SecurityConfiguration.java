package com.dovecry.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.dovecry.security.SecurityAuthenticationFilter;
import com.dovecry.security.SecurityAuthorizationFilter;
import com.dovecry.security.UserServiceImp;

@EnableWebSecurity
@PropertySource("classpath:security.properties")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	@Value("${jwt.registerurl}")
    public String SIGN_UP_URL;

	private UserServiceImp userDetailsService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public SecurityConfiguration(UserServiceImp userDetailsService,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        		http.cors().and().csrf().disable().authorizeRequests()
	                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
	                .and()
	                .addFilter(new SecurityAuthenticationFilter(authenticationManager()))
	                .addFilter(new SecurityAuthorizationFilter(authenticationManager()))
	                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	    } 
	
	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	 
	@Bean
	  CorsConfigurationSource corsConfigurationSource() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
	    return source;
	  }

}

package com.murariwalake.photoapp.api.users.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.murariwalake.photoapp.api.users.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{

	private Environment environment;
	private UserService usersService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public WebSecurity(Environment aEnvironment,
			UserService aUserService,
			BCryptPasswordEncoder aBCryptPasswordEncoder) {
		this.environment = aEnvironment;
		this.usersService = aUserService;
		this.bCryptPasswordEncoder = aBCryptPasswordEncoder;
	}

	@Override 
	protected void configure(HttpSecurity aHttpSecurity) throws Exception{
		aHttpSecurity.csrf().disable();

		aHttpSecurity.authorizeRequests()
		.antMatchers("/users/**").permitAll()
		.antMatchers(environment.getProperty("api.actuator.url.path")).permitAll()
		.and()
		.addFilter(getAuthenticationFilter());

		aHttpSecurity.headers().frameOptions().disable();
	}
	
	private AuthenticationFilter getAuthenticationFilter() throws Exception{
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(usersService, environment, authenticationManager());
		//authenticationFilter.setAuthenticationManager(authenticationManager()); 
		authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
		return authenticationFilter;
	}
	
    @Override
    protected void configure(AuthenticationManagerBuilder aAuth) throws Exception {
    	aAuth.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);
    }
}

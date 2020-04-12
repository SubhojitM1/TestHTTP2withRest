package com.test.http2;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.session.HttpSessionEventPublisher;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().
		antMatchers(HttpMethod.GET, "/actuator/*");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.logout()
//		.logoutUrl("/perform_logout")
		.logoutSuccessUrl("/login")
		.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID").and()
//		.addFilter(new Filter(){
//			 @Override
//			 public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//				 servletRequest.get
//				 filterChain.doFilter(servletRequest, servletResponse);
//			 }
//		})
		.headers()
//		.httpStrictTransportSecurity().disable()
		.and().httpBasic().and().formLogin() 
		.and().authorizeRequests().anyRequest().authenticated()
		.and().sessionManagement().sessionFixation().migrateSession()
		.sessionCreationPolicy(SessionCreationPolicy.ALWAYS).maximumSessions(1)
		;
		
	}
	
	
	@Bean
	public UserDetailsService myUserDetails() {
		return new InMemoryUserDetailsManager(User.withUsername("user")
				 .password("secret")
				 .passwordEncoder(password->passwordEncoder().encode(password))
				 .roles("USER")
				 .build());
	}
	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}

}

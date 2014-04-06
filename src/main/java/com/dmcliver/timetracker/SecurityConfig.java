package com.dmcliver.timetracker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dmcliver.timetracker.service.SysUserDetailsService;

@Configuration
@EnableWebSecurity
@Import(AppConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	public void configure(HttpSecurity sec) throws Exception{
		
		sec.authorizeRequests()
			.antMatchers("/**/*.css")
			.permitAll()
	   		.antMatchers("/login")   		   		
	   		.permitAll()
	   		.antMatchers("/badlogin")
	   		.permitAll()
	   		.antMatchers("/register")
	   		.permitAll()
	   		.antMatchers("/**")
	   		.access("hasRole('su')")
	   .and()
	   .formLogin()
	   		.loginPage("/login")
	   		.loginProcessingUrl("/jsecuritycheck")
	   		.defaultSuccessUrl("/")
	   		.failureUrl("/badlogin")
   		.and()
   		.logout().logoutUrl("/logout");
	}
	
	public void configure(AuthenticationManagerBuilder builder) throws Exception{
		
		builder.userDetailsService(userDetailsService())
			   .passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
		return encoder;
	}

	@Bean
	@Override
	public UserDetailsService userDetailsService(){
		return new SysUserDetailsService();
	}
}

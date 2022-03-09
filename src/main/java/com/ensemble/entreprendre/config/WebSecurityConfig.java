package com.ensemble.entreprendre.config;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ensemble.entreprendre.security.JwtAuthenticationEntryPoint;
import com.ensemble.entreprendre.security.JwtAuthenticationFilter;
import com.ensemble.entreprendre.security.helper.JwtTokenUtilBean;

@Configuration
@Order(1)
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	private String key = "pass.pass.pass";

	private Random random = new SecureRandom();

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public JwtAuthenticationFilter authenticationTokenFilterBean() {
		return new JwtAuthenticationFilter();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
		httpSecurity.cors().and().csrf().disable().authorizeRequests()
				.antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**", "/api/companies",
						"/api/activities", "/api/jobs", "/api/internStatus", "/api/internTypes", "/api/users/register", "/api/users/authenticate")
				.permitAll().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
		// httpSecurity.csrf().disable()
		// // dont authenticate this particular request
		// .authorizeRequests().antMatchers("/authenticate").permitAll().
		// antMatchers(HttpMethod.OPTIONS, "/**")
		// .permitAll().
		// // all other requests need to be authenticated
		// anyRequest().authenticated().and().
		// // make sure we use stateless session; session won't be used to
		// // store user's state.
		// exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
		// .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		// httpSecurity.addFilterBefore(jwtRequestFilter,
		// UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	protected JwtTokenUtilBean getTokenUtil() {
		return new JwtTokenUtilBean(this.key);
	}

	public Stream<Character> getRandomSpecialChars(int count) {
		Random random = new SecureRandom();
		IntStream specialChars = random.ints(count, 33, 45);

		return specialChars.mapToObj(data -> (char) data);
	}

	public Stream<Character> getRandomNumbers(int count) {
		IntStream numbers = random.ints(count, 48, 57);

		return numbers.mapToObj(data -> (char) data);
	}

	public Stream<Character> getRandomAlphabets(int count, boolean upperCase) {
		IntStream characters = null;
		if (upperCase) {
			characters = random.ints(count, 65, 90);
		} else {
			characters = random.ints(count, 97, 122);
		}
		return characters.mapToObj(data -> (char) data);
	}

	public String generateSecureRandomPassword() {
		Stream<Character> pwdStream = Stream.concat(getRandomNumbers(1), Stream.concat(getRandomSpecialChars(1),
				Stream.concat(getRandomAlphabets(2, true), getRandomAlphabets(4, false))));
		List<Character> charList = pwdStream.collect(Collectors.toList());
		Collections.shuffle(charList);
		String password = charList.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
				.toString();

		System.out.println(password);
		password = password.replace('"', '&');
		return password;
	}

	// public static void main(String[] args) {
	// System.err.println(new
	// WebSecurityConfig().passwordEncoder().encode("mypass"));
	// }
}

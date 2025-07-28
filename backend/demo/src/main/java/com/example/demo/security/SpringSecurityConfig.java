package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Bean // 비밀번호 암호화 : 단방향
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private final JwtAuthenticationFilter jwtFilter;
  private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter, UserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

	// 인증 및 인가 : Lambda DSL
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http // Security가 적용될 URI
				.authorizeHttpRequests(authrize -> authrize
						.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
						.requestMatchers("/", "/test").permitAll()
						.anyRequest().authenticated());

		http.csrf(csrf -> csrf.disable());
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web
				.ignoring()
				.requestMatchers("/images/**", "/js/**", "/css/**"); // 예외 대상
	}

}

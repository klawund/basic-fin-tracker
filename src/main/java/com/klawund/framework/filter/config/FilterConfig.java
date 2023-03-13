package com.klawund.framework.filter.config;

import com.klawund.fin.role.Role;
import com.klawund.framework.logging.filter.CurrentRequestURILogFilter;
import com.klawund.framework.multitenancy.filter.CurrentTenantExtractorFilter;
import com.klawund.framework.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class FilterConfig
{
	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final CurrentTenantExtractorFilter currentTenantExtractorFilter;
	private final CurrentRequestURILogFilter currentRequestURILogFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		return http
			.csrf().disable()
			.authorizeHttpRequests(authorize -> {
				authorize.requestMatchers("/auth/**").permitAll();
				authorize.requestMatchers("/users/**").hasAuthority(Role.ADMIN.name());
				authorize.anyRequest().authenticated();
			})
			.sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterAfter(currentTenantExtractorFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterAfter(currentRequestURILogFilter, CurrentTenantExtractorFilter.class)
		.build();
	}
}

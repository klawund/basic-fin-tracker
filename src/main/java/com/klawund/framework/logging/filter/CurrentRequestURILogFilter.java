package com.klawund.framework.logging.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class CurrentRequestURILogFilter extends OncePerRequestFilter
{
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest req, @NonNull HttpServletResponse res,
		@NonNull FilterChain chain) throws ServletException, IOException
	{
		log.trace("Current request URI: {}", req.getRequestURI());
		chain.doFilter(req, res);
	}
}

package com.klawund.framework.multitenancy.filter;

import com.klawund.framework.multitenancy.context.TenantContext;
import com.klawund.framework.security.CurrentUserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class CurrentTenantExtractorFilter extends OncePerRequestFilter
{
	private final CurrentUserDTO currentUserDTO;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest req, @NonNull HttpServletResponse res,
		@NonNull FilterChain chain) throws ServletException, IOException
	{
		if (currentUserDTO.getUsername() != null)
		{
			TenantContext.setCurrentTenantId(currentUserDTO.getUsername());
		}

		try
		{
			chain.doFilter(req, res);
		}
		finally
		{
			TenantContext.setCurrentTenantId(TenantContext.DEFAULT_TENANT);
		}
	}
}

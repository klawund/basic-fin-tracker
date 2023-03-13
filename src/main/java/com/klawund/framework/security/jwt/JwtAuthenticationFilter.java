package com.klawund.framework.security.jwt;

import com.klawund.fin.user.User;
import com.klawund.fin.user.UserService;
import com.klawund.framework.security.CurrentUserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
	private final JwtService jwtService;
	private final UserService userService;
	private final CurrentUserDTO currentUserDTO;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
		throws ServletException, IOException
	{
		final String authHeader = request.getHeader(Constants.AUTH_HEADER_NAME);

		if (authHeader == null || !authHeader.startsWith(Constants.BEARER_TOKEN_PREFIX))
		{
			filterChain.doFilter(request, response);
			return;
		}

		final String jwt = authHeader.substring(Constants.BEARER_TOKEN_PREFIX.length());
		final String username = jwtService.extractUsername(jwt);

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			final User userDetails = (User) userService.loadUserByUsername(username);
			setCurrentUser(userDetails);

			if (jwtService.isTokenValid(jwt, userDetails))
			{
				final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}

	void setCurrentUser(User user)
	{
		currentUserDTO.setId(user.getId());
		currentUserDTO.setUsername(user.getUsername());
		currentUserDTO.setFirstName(user.getFirstName());
		currentUserDTO.setLastName(user.getLastName());
		currentUserDTO.setEmail(user.getEmail());
	}

	static class Constants
	{
		private Constants()
		{
		}

		static final String AUTH_HEADER_NAME = "Authorization";
		static final String BEARER_TOKEN_PREFIX = "Bearer ";
	}
}

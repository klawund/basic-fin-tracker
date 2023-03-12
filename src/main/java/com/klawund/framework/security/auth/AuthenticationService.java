package com.klawund.framework.security.auth;

import com.klawund.fin.role.Role;
import com.klawund.fin.user.UserService;
import com.klawund.fin.user.User;
import com.klawund.framework.security.auth.dto.AuthenticationRequestDTO;
import com.klawund.framework.security.auth.dto.RegisterRequestDTO;
import com.klawund.framework.security.auth.dto.TokenDTO;
import com.klawund.framework.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService
{
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public TokenDTO register(RegisterRequestDTO request)
	{
		var user = User.builder()
			.firstName(request.getFirstName())
			.lastName(request.getLastName())
			.email(request.getEmail())
			.username(request.getUsername())
			.password(passwordEncoder.encode(request.getPassword()))
			.role(Role.USER)
			.build();

		userService.save(user);

		var jwt = jwtService.generateToken(user);
		return TokenDTO.builder()
			.token(jwt)
			.build();
	}

	public TokenDTO authenticate(AuthenticationRequestDTO request)
	{
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				request.getUsername(),
				request.getPassword())
		);

		var user = userService.loadUserByUsername(request.getUsername());

		var jwt = jwtService.generateToken(user);
		return TokenDTO.builder()
			.token(jwt)
			.build();
	}
}

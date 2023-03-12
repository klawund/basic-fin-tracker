package com.klawund.framework.security.auth;

import com.klawund.framework.security.auth.dto.AuthenticationRequestDTO;
import com.klawund.framework.security.auth.dto.RegisterRequestDTO;
import com.klawund.framework.security.auth.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController
{
	private final AuthenticationService service;

	@PostMapping("/register")
	public ResponseEntity<TokenDTO> register(@RequestBody RegisterRequestDTO request)
	{
		return ResponseEntity.ok(service.register(request));
	}

	@PostMapping("/authenticate")
	public ResponseEntity<TokenDTO> register(@RequestBody AuthenticationRequestDTO request)
	{
		return ResponseEntity.ok(service.authenticate(request));
	}
}

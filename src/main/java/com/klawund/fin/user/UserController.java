package com.klawund.fin.user;

import com.klawund.fin.user.dto.UserDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController
{
	private final UserService service;

	@GetMapping
	public ResponseEntity<List<UserDTO>> getAll()
	{
		return ResponseEntity.ok(service.findAll());
	}
}

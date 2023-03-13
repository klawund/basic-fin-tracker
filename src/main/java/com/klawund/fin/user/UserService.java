package com.klawund.fin.user;

import com.klawund.fin.user.dto.UserDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService
{
	public static final String SUPER_USER_USERNAME = "super";

	private final UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		return repository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	public List<UserDTO> findAll()
	{
		List<User> allUsers = repository.findAll();
		return allUsers.stream().map(UserDTO::new).toList();
	}
}

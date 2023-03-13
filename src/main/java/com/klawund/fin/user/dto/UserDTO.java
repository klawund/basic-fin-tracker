package com.klawund.fin.user.dto;

import com.klawund.fin.role.Role;
import com.klawund.fin.user.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO
{
	public UserDTO(User user)
	{
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.role = user.getRole();
	}

	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private Role role;
}

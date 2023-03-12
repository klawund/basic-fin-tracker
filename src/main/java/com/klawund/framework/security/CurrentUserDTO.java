package com.klawund.framework.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Getter
@Setter
public class CurrentUserDTO
{
	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
}

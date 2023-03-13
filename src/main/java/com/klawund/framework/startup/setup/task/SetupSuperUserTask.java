package com.klawund.framework.startup.setup.task;

import com.klawund.fin.role.Role;
import com.klawund.fin.user.User;
import com.klawund.fin.user.UserRepository;
import com.klawund.fin.user.UserService;
import com.klawund.framework.admin.config.data.AdminConfigData;
import com.klawund.framework.security.auth.AuthenticationService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetupSuperUserTask implements SetupTask
{
	private final UserRepository userRepository;
	private final AdminConfigData adminConfigData;
	private final AuthenticationService authenticationService;

	@Override
	public boolean shouldRun()
	{
		Optional<User> existingSuper = userRepository.findByUsername(UserService.SUPER_USER_USERNAME);
		return existingSuper.isEmpty();
	}

	@Override
	public void run()
	{
		var superUser = User.builder()
			.username(UserService.SUPER_USER_USERNAME)
			.password(authenticationService.getEncodedPassword(adminConfigData.getSuperPassword()))
			.role(Role.ADMIN)
			.build();

		userRepository.save(superUser);
	}
}

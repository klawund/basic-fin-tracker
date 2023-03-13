package com.klawund.framework.startup.setup;

import com.klawund.framework.startup.setup.task.SetupSuperUserTask;
import com.klawund.framework.startup.setup.task.SetupTask;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SetupConfig
{
	private final SetupSuperUserTask setupSuperUserTask;

	private final List<SetupTask> tasks = new ArrayList<>();

	void runTasks()
	{
		for (SetupTask task : tasks)
		{
			if (task.shouldRun())
			{
				task.run();
			}
		}
	}

	void buildTaskList()
	{
		tasks.add(setupSuperUserTask);
	}

	@PostConstruct
	void postConstruct()
	{
		buildTaskList();
		runTasks();
	}
}

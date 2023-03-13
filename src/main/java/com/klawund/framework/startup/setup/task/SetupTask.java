package com.klawund.framework.startup.setup.task;

public interface SetupTask
{
	boolean shouldRun();

	void run();
}

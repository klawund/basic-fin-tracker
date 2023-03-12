package com.klawund.framework.multitenancy.context;

public final class TenantContext
{
	private TenantContext()
	{
	}

	public static final String DEFAULT_TENANT = "PUBLIC";

	static final ThreadLocal<String> CURRENT_TENANT_ID = ThreadLocal.withInitial(() -> DEFAULT_TENANT);

	public static String getCurrentTenantId()
	{
		return CURRENT_TENANT_ID.get();
	}

	public static void setCurrentTenantId(String tenantId)
	{
		CURRENT_TENANT_ID.set(tenantId.toUpperCase());
	}
}

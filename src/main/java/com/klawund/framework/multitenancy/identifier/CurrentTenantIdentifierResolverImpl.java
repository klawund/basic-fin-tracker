package com.klawund.framework.multitenancy.identifier;

import com.klawund.framework.multitenancy.context.TenantContext;
import java.util.Map;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver, HibernatePropertiesCustomizer
{
	@Override
	public String resolveCurrentTenantIdentifier()
	{
		return TenantContext.getCurrentTenantId();
	}

	@Override
	public boolean validateExistingCurrentSessions()
	{
		return true;
	}

	@Override
	public void customize(Map<String, Object> hibernateProperties)
	{
		hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
	}
}

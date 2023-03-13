package com.klawund.framework.logging;

import com.klawund.framework.multitenancy.context.TenantContext;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.util.SortedArrayStringMap;

@Plugin(name = "CurrentUserRewritePolicy", category = "Core", elementType = "rewritePolicy", printObject = true)
public final class CurrentUserRewritePolicy implements RewritePolicy
{
	@Override
	public LogEvent rewrite(LogEvent source)
	{
		final String currentUserName = TenantContext.getCurrentTenantId();
		if (currentUserName == null || currentUserName.isBlank() || TenantContext.DEFAULT_TENANT.equals(currentUserName))
		{
			return source;
		}

		final Log4jLogEvent.Builder builder = new Log4jLogEvent.Builder(source);
		final Map<String, String> context = new HashMap<>(source.getContextData().toMap());

		context.put("current.user", "[" + currentUserName + "]");
		builder.setContextData(new SortedArrayStringMap(context));
		return builder.build();
	}

	@PluginFactory
	public static CurrentUserRewritePolicy createPolicy()
	{
		return new CurrentUserRewritePolicy();
	}
}

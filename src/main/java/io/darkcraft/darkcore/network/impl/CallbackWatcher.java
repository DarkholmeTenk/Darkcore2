package io.darkcraft.darkcore.network.impl;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalNotification;

import io.darkcraft.darkcore.network.command.ClientCommandCallback;

public class CallbackWatcher
{
	private static final Logger LOGGER = LogManager.getLogger(CallbackWatcher.class);
	private final Cache<Integer, CallbackHolder<?>> cache = CacheBuilder.newBuilder()
			.expireAfterWrite(30, TimeUnit.SECONDS)
			.removalListener((RemovalNotification<Integer, CallbackHolder<?>> e)-> evictionWatch(e))
			.build();

	private void evictionWatch(RemovalNotification<Integer, CallbackHolder<?>> notification)
	{
		RemovalCause cause = notification.getCause();
		if(cause == RemovalCause.EXPIRED)
			LOGGER.error("Unhandled callback [{}]: {}", notification.getKey(), notification.getValue());
		if(cause == RemovalCause.REPLACED)
			LOGGER.error("Callback replaced [{}]: {}", notification.getKey(), notification.getValue());
	}

	void watch(int ack, CallbackHolder<?> holder)
	{
		cache.put(ack, holder);
	}

	void handle(int ack, ClientCommandCallback command)
	{
		CallbackHolder<?> holder = cache.getIfPresent(ack);
		cache.invalidate(ack);
		if(holder != null)
			holder.run(command);
	}
}

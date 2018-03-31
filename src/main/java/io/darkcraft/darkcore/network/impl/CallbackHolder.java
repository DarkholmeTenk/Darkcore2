package io.darkcraft.darkcore.network.impl;

import java.util.function.Consumer;

import io.darkcraft.darkcore.network.command.ClientCommandCallback;

public class CallbackHolder<T extends ClientCommandCallback>
{
	private final Consumer<T> callback;
	private boolean handled = false;

	CallbackHolder(Consumer<T> consumer)
	{
		this.callback = consumer;
	}

	void run(ClientCommandCallback command)
	{
		callback.accept((T) command);
	}

	boolean isHandled()
	{
		return handled;
	}
}

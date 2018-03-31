package io.darkcraft.darkcore.network.impl;

import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import io.darkcraft.darkcore.network.command.ClientCommandCallback;
import io.darkcraft.darkcore.network.command.ClientCommandFireAndForget;
import io.darkcraft.darkcore.network.exception.NetworkCommandTypeException;

public class CommandHandlerClient implements IMessageHandler<CommandContainer, CommandContainer>
{
	private final CallbackWatcher watcher;

	CommandHandlerClient(CallbackWatcher watcher)
	{
		this.watcher = watcher;
	}

	@Override
	public CommandContainer onMessage(CommandContainer message, MessageContext ctx)
	{
		Object command = message.command;
		if(command instanceof ClientCommandCallback)
			watcher.handle(message.ack, (ClientCommandCallback) command);
		else if(command instanceof ClientCommandFireAndForget)
			((ClientCommandFireAndForget)command).run(ctx);
		else
			throw new NetworkCommandTypeException("Unacceptable client command type returned. " + command);
		return null;
	}
}

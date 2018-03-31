package io.darkcraft.darkcore.network.impl;

import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import io.darkcraft.darkcore.network.command.ClientCommandCallback;
import io.darkcraft.darkcore.network.command.ServerCommandCallback;
import io.darkcraft.darkcore.network.command.ServerCommandFireAndForget;
import io.darkcraft.darkcore.network.exception.NetworkCommandTypeException;

public class CommandHandlerServer implements IMessageHandler<CommandContainer, CommandContainer>
{
	CommandHandlerServer()
	{
	}

	@Override
	public CommandContainer onMessage(CommandContainer message, MessageContext ctx)
	{
		Object command = message.command;
		if(command instanceof ServerCommandCallback)
		{
			ClientCommandCallback result = ((ServerCommandCallback<?>) command).run(ctx);
			return new CommandContainer(message.ack, result);
		}
		else if(command instanceof ServerCommandFireAndForget)
		{
			((ServerCommandFireAndForget) command).run(ctx);
			return null;
		}
		else
			throw new NetworkCommandTypeException("Unacceptable server command type returned. " + command);
	}
}

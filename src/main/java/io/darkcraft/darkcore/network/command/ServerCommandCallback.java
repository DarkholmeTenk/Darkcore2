package io.darkcraft.darkcore.network.command;

import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class ServerCommandCallback<T extends ClientCommandCallback>
{
	public abstract T run(MessageContext context);
}

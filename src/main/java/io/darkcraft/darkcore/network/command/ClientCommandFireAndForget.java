package io.darkcraft.darkcore.network.command;

import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class ClientCommandFireAndForget
{
	public abstract void run(MessageContext ctx);
}

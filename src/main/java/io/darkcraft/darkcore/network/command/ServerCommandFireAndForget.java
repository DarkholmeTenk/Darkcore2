package io.darkcraft.darkcore.network.command;

import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class ServerCommandFireAndForget
{
	public abstract void run(MessageContext ctx);
}

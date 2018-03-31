package io.darkcraft.darkcore.network.impl;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import io.darkcraft.darkcore.network.DarkcoreNetworkWrapper;
import io.darkcraft.darkcore.network.command.ClientCommandCallback;
import io.darkcraft.darkcore.network.command.ClientCommandFireAndForget;
import io.darkcraft.darkcore.network.command.ServerCommandCallback;
import io.darkcraft.darkcore.network.command.ServerCommandFireAndForget;

public class DarkcoreNetworkWrapperImpl implements DarkcoreNetworkWrapper
{
	private final SimpleNetworkWrapper wrapper;
	private final CallbackWatcher watcher;
	private final CommandHandlerServer serverHandler;
	private final CommandHandlerClient clientHandler;

	private AtomicInteger counter;

	public DarkcoreNetworkWrapperImpl(String modID)
	{
		wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(modID);
		watcher = new CallbackWatcher();
		serverHandler = new CommandHandlerServer();
		clientHandler = new CommandHandlerClient(watcher);

		wrapper.registerMessage(serverHandler, CommandContainer.class, 0, Side.SERVER);
		wrapper.registerMessage(clientHandler, CommandContainer.class, 0, Side.CLIENT);
	}

	@Override
	public void sendToServer(ServerCommandFireAndForget command)
	{
		wrapper.sendToServer(new CommandContainer(0, command));
	}

	@Override
	public <T extends ClientCommandCallback> void sendToServer(ServerCommandCallback<T> command,
			Consumer<T> callback)
	{
		int ack = counter.getAndIncrement();
		watcher.watch(ack, new CallbackHolder<>(callback));
		wrapper.sendToServer(new CommandContainer(ack, command));
	}

	@Override
	public void sendToAll(ClientCommandFireAndForget message)
	{
		wrapper.sendToAll(new CommandContainer(0, message));
	}

	@Override
	public void sendTo(ClientCommandFireAndForget message, EntityPlayerMP player)
	{
		wrapper.sendTo(new CommandContainer(0, message), player);
	}

	@Override
	public void sendToAllAround(ClientCommandFireAndForget message, TargetPoint point)
	{
		wrapper.sendToAllAround(new CommandContainer(0, message), point);
	}

	@Override
	public void sendToDimension(ClientCommandFireAndForget message, int dimensionId)
	{
		wrapper.sendToDimension(new CommandContainer(0, message), dimensionId);
	}
}

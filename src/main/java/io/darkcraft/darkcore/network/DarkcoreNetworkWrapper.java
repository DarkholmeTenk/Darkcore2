package io.darkcraft.darkcore.network;

import java.util.function.Consumer;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import io.darkcraft.darkcore.network.command.ClientCommandCallback;
import io.darkcraft.darkcore.network.command.ClientCommandFireAndForget;
import io.darkcraft.darkcore.network.command.ServerCommandCallback;
import io.darkcraft.darkcore.network.command.ServerCommandFireAndForget;
import io.darkcraft.darkcore.network.impl.DarkcoreNetworkWrapperImpl;

public interface DarkcoreNetworkWrapper
{
	void sendToServer(ServerCommandFireAndForget command);

	<T extends ClientCommandCallback> void sendToServer(ServerCommandCallback<T> command, Consumer<T> callback);

	void sendToAll(ClientCommandFireAndForget message);

	void sendTo(ClientCommandFireAndForget message, EntityPlayerMP player);

	void sendToAllAround(ClientCommandFireAndForget message, TargetPoint point);

	void sendToDimension(ClientCommandFireAndForget message, int dimensionId);

	public static DarkcoreNetworkWrapper get(String modID)
	{
		return new DarkcoreNetworkWrapperImpl(modID);
	}
}
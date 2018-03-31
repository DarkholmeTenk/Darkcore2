package io.darkcraft.darkcore.network.impl;

import java.io.IOException;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import io.darkcraft.darkcore.nbt.impl.wrappers.PolymorphicWrapper;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.util.NBTHelper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

public class CommandContainer implements IMessage
{
	private static final NBTMapper mapper = NBTHelper.INSTANCE.buildDefaultMapper().build();
	private static final PolymorphicWrapper poly = mapper.getPolymorphic();

	int ack;
	Object command;

	public CommandContainer() {}

	CommandContainer(int ack, Object command)
	{
		this.ack = ack;
		this.command = command;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		try(ByteBufInputStream stream = new ByteBufInputStream(buf))
		{
			ack = buf.readInt();
			NBTTagCompound nbt = CompressedStreamTools.readCompressed(stream);
			command = poly.readFromNBT(nbt);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		try(ByteBufOutputStream stream = new ByteBufOutputStream(buf))
		{
			NBTTagCompound nbt = new NBTTagCompound();
			poly.writeToNBT(nbt, command);
			stream.writeInt(ack);
			CompressedStreamTools.writeCompressed(nbt, stream);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}

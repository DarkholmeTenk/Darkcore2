package io.darkcraft.darkcore.nbt.impl.wrappers.array;

import static io.darkcraft.darkcore.nbt.impl.wrappers.array.ArrayConstants.getKey;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.mapper.NBTWriter;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter.NBTObjWriter;

final class ArrayWriter<T> implements NBTObjWriter<T[]>
{
	private final NBTWriter<T> writer;
	public ArrayWriter(NBTWriter<T> writer)
	{
		this.writer = writer;
	}

	@Override
	public void writeToNBT(NBTTagCompound list, T[] value)
	{
		list.setInteger(ArrayConstants.SIZE_KEY, value.length);
		int i = 0;
		for(T t : value)
		{
			if(t != null)
				writer.writeToNBT(list, getKey(i), t);
			i++;
		}
	}
}

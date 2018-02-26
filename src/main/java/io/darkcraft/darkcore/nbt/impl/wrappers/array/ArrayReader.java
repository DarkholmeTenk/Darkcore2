package io.darkcraft.darkcore.nbt.impl.wrappers.array;

import static io.darkcraft.darkcore.nbt.impl.wrappers.array.ArrayConstants.SIZE_KEY;
import static io.darkcraft.darkcore.nbt.impl.wrappers.array.ArrayConstants.getKey;

import java.lang.reflect.Array;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTReader.NBTObjReader;

final class ArrayReader<T> implements NBTObjReader<T[]>
{
	private final NBTReader<T> reader;
	private final Class<T> clz;

	public ArrayReader(NBTReader<T> reader, Class<T> clz)
	{
		this.reader = reader;
		this.clz = clz;
	}

	@Override
	public T[] readFromNBT(NBTTagCompound list)
	{
		int size = list.getInteger(SIZE_KEY);
		T[] arr = (T[]) Array.newInstance(clz, size);
		for(int i = 0; i < size; i++)
		{
			String key = getKey(i);
			if(list.hasKey(key))
				arr[i] = reader.readFromNBT(list, key);
		}
		return arr;
	}
}

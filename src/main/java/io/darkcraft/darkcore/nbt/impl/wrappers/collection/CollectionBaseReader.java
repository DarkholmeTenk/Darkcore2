package io.darkcraft.darkcore.nbt.impl.wrappers.collection;

import java.util.Collection;
import java.util.function.Supplier;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.mapper.NBTReader;

public class CollectionBaseReader<T, U extends Collection<T>> implements NBTReader<U>
{
	private final NBTReader<T> reader;
	private final Supplier<U> supplier;

	CollectionBaseReader(NBTReader<T> reader, Supplier<U> supplier)
	{
		this.reader = reader;
	}

	@Override
	public U readFromNBT(NBTTagCompound nbt, String id)
	{
		U u = supplier.get();
		NBTTagCompound list = nbt.getCompoundTag(id);
		int size = list.getInteger(CollectionConstants.SIZE_KEY);
		for(int i = 0; i < size; i++)
		{
			String key = CollectionConstants.getKey(i);
			if(nbt.hasKey(key))
				u.add(reader.readFromNBT(list, key));
			else
				u.add(null);
		}
		return u;
	}

}

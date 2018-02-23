package io.darkcraft.darkcore.nbt.impl.wrappers.collection;

import java.util.Collection;
import java.util.function.Supplier;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTReader.NBTObjReader;

public class CollectionBaseReader<T, U extends Collection<T>> implements NBTObjReader<U>
{
	private final NBTReader<T> reader;
	private final Supplier<U> supplier;

	public CollectionBaseReader(NBTReader<T> reader, Supplier<U> supplier)
	{
		this.reader = reader;
		this.supplier = supplier;
	}

	@Override
	public U readFromNBT(NBTTagCompound list)
	{
		U u = supplier.get();
		int size = list.getInteger(CollectionConstants.SIZE_KEY);
		for(int i = 0; i < size; i++)
		{
			String key = CollectionConstants.getKey(i);
			if(list.hasKey(key))
				u.add(reader.readFromNBT(list, key));
			else
				u.add(null);
		}
		return u;
	}

}

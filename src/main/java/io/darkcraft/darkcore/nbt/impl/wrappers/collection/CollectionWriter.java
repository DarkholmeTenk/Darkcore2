package io.darkcraft.darkcore.nbt.impl.wrappers.collection;

import static io.darkcraft.darkcore.nbt.impl.wrappers.collection.CollectionConstants.getKey;

import java.util.Collection;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.mapper.NBTWriter;

public class CollectionWriter<T> implements NBTWriter<Collection<T>>
{

	private final NBTWriter<T> writer;
	public CollectionWriter(NBTWriter<T> writer)
	{
		this.writer = writer;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, String key, Collection<T> value)
	{
		NBTTagCompound list = new NBTTagCompound();
		list.setInteger(CollectionConstants.SIZE_KEY, value.size());
		int i = 0;
		for(T t : value)
		{
			if(t != null)
				writer.writeToNBT(list, getKey(i), t);
			i++;
		}
		nbt.setTag(key, list);
	}

}

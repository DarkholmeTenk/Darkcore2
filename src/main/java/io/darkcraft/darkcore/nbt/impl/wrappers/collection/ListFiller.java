package io.darkcraft.darkcore.nbt.impl.wrappers.collection;

import static io.darkcraft.darkcore.nbt.impl.wrappers.collection.CollectionConstants.SIZE_KEY;
import static io.darkcraft.darkcore.nbt.impl.wrappers.collection.CollectionConstants.getKey;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.mapper.NBTFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTFiller.NBTObjFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;

public class ListFiller<T, U extends List<T>> implements NBTObjFiller<U>
{
	private final NBTReader<T> reader;
	private final NBTFiller<T> filler;

	ListFiller(NBTReader<T> reader, NBTFiller<T> filler)
	{
		this.reader = reader;
		this.filler = filler;
	}

	@Override
	public void fillFromNBT(NBTTagCompound nbt, U existing)
	{
		int originalSize = existing.size();
		int newSize = nbt.getInteger(SIZE_KEY);
		if(newSize < originalSize)
			for(int i = originalSize; i > newSize; i--)
				existing.remove(i - 1);
		for(int i = 0; i < newSize; i++)
		{
			if((filler == null) || (i >= originalSize))
				existing.add(i, reader.readFromNBT(nbt, getKey(i)));
			else
				filler.fillFromNBT(nbt, getKey(i), existing.get(i));
		}
	}
}

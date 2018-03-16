package io.darkcraft.darkcore.nbt.impl.wrappers.array;

import static io.darkcraft.darkcore.nbt.impl.wrappers.array.ArrayConstants.SIZE_KEY;
import static io.darkcraft.darkcore.nbt.impl.wrappers.array.ArrayConstants.getKey;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.mapper.NBTFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTFiller.NBTObjFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.util.NBTHelper;

final class ArrayFiller<T> implements NBTObjFiller<T[]>
{
	private final NBTReader<T> reader;
	private final NBTFiller<T> filler;

	ArrayFiller(NBTReader<T> reader, NBTFiller<T> filler)
	{
		this.reader = reader;
		this.filler = filler;
	}

	@Override
	public void fillFromNBT(NBTTagCompound nbt, T[] existing)
	{
		int originalSize = existing.length;
		int newSize = nbt.getInteger(SIZE_KEY);
		if(originalSize != newSize)
			throw new IllegalArgumentException("New size and Original size must be the same, use a list instead");
		for(int i = 0; i < newSize; i++)
		{
			if(!NBTHelper.fill(filler, nbt, getKey(i), existing[i]))
				existing[i] = NBTHelper.read(nbt, getKey(i), reader).orElse(null);
		}
	}

	@Override
	public boolean isValid(NBTTagCompound nbt, T[] existing)
	{
		return true;
	}
}

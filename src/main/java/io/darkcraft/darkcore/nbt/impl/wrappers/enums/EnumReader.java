package io.darkcraft.darkcore.nbt.impl.wrappers.enums;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.exception.NBTReadingException;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;

class EnumReader<T extends Enum<T>> implements NBTReader<T>
{
	private static final Logger LOGGER = LogManager.getLogger(EnumReader.class);
	private final Class<T> clz;
	private final T[] values;
	EnumReader(Class<T> clz)
	{
		this.clz = clz;
		this.values = clz.getEnumConstants();
	}

	@Override
	public T readFromNBT(NBTTagCompound nbt, String id)
	{
		if(nbt.hasKey(id))
		{
			int ordinal = nbt.getInteger(id);
			if((ordinal >= 0) && (ordinal < values.length))
				return values[ordinal];
			LOGGER.error("Ordinal " + ordinal + " is not valid for " + clz);
			throw new NBTReadingException("Ordinal " + ordinal + " is not valid for " + clz);
		}
		return null;
	}
}

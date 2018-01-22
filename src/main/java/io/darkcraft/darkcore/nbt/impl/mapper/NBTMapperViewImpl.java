package io.darkcraft.darkcore.nbt.impl.mapper;

import java.util.Map;

import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;

public class NBTMapperViewImpl extends NBTMapperImpl
{

	public NBTMapperViewImpl(Map<Class<?>, NBTWriter<?>> globalWriters, Map<Class<?>, NBTReader<?>> globalReaders,
			Class<?> viewClazz)
	{
		super(globalWriters, globalReaders);
	}

}

package io.darkcraft.darkcore.nbt.util;

import java.util.Map;

import io.darkcraft.darkcore.nbt.impl.mapper.NBTMapperImpl;
import io.darkcraft.darkcore.nbt.impl.mapper.NBTMapperViewImpl;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;

public class NBTMapperBuilder
{
	private final Map<Class<?>, NBTWriter<?>> globalWriters;
	private final Map<Class<?>, NBTReader<?>> globalReaders;

	private Class<?> viewClazz;

	NBTMapperBuilder(
			Map<Class<?>, NBTWriter<?>> globalWriters,
			Map<Class<?>, NBTReader<?>> globalReaders)
	{
		this.globalWriters = globalWriters;
		this.globalReaders = globalReaders;
	}

	public NBTMapperBuilder withView(Class<?> viewClazz)
	{
		this.viewClazz = viewClazz;
		return this;
	}

	public NBTMapper build()
	{
		if(viewClazz == null)
			return new NBTMapperImpl(globalWriters, globalReaders);
		else
			return new NBTMapperViewImpl(globalWriters, globalReaders, viewClazz);
	}
}

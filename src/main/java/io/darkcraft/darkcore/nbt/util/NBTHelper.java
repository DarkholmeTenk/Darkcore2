package io.darkcraft.darkcore.nbt.util;

import java.util.HashMap;
import java.util.Map;

import io.darkcraft.darkcore.nbt.impl.wrappers.PrimitiveWrappers;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;

public class NBTHelper
{
	public static NBTHelper INSTANCE = PrimitiveWrappers.addWrappers(new NBTHelper());

	private final Map<Class<?>, NBTWriter<?>> globalWriters = new HashMap<>();
	private final Map<Class<?>, NBTReader<?>> globalReaders = new HashMap<>();

	NBTHelper(){};

	public <T> void addGlobalWriter(Class<T> clazz, NBTWriter<T> writer)
	{
		globalWriters.put(clazz, writer);
	}

	public <T> void addGlobalReader(Class<T> clazz, NBTReader<T> reader)
	{
		globalReaders.put(clazz, reader);
	}

	public NBTMapperBuilder buildMapper()
	{
		return new NBTMapperBuilder(globalWriters, globalReaders);
	}
}

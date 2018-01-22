package io.darkcraft.darkcore.nbt.impl.mapper;

import static io.darkcraft.darkcore.nbt.util.ClassHelper.wrapPrimitive;

import java.util.Map;

import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;

public class NBTMapperImpl implements NBTMapper
{
	private final Map<Class<?>, NBTWriter<?>> globalWriters;
	private final Map<Class<?>, NBTReader<?>> globalReaders;

	public NBTMapperImpl(Map<Class<?>, NBTWriter<?>> globalWriters, Map<Class<?>, NBTReader<?>> globalReaders)
	{
		this.globalReaders = globalReaders;
		this.globalWriters = globalWriters;
	}

	@Override
	public <T> NBTWriter<T> getWriter(Class<T> clazz)
	{
		return (NBTWriter<T>) globalWriters.get(wrapPrimitive(clazz));
	}

	@Override
	public <T> NBTReader<T> getReader(Class<T> clazz)
	{
		return (NBTReader<T>) globalReaders.get(wrapPrimitive(clazz));
	}

}

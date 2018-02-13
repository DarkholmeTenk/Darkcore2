package io.darkcraft.darkcore.nbt.impl.mapper;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.darkcraft.darkcore.nbt.mapper.NBTFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;
import io.darkcraft.darkcore.nbt.mapper.PartialMapper;

public class ClassPartialMapper implements PartialMapper
{
	private final Set<Class<?>> classes = new HashSet<>();
	private final Map<Class<?>, NBTReader<?>> readers = new HashMap<>();
	private final Map<Class<?>, NBTWriter<?>> writers = new HashMap<>();
	private final Map<Class<?>, NBTFiller<?>> fillers = new HashMap<>();

	public <T> void register(Class<T> clazz, NBTReader<T> reader, NBTWriter<T> writer)
	{
		register(clazz, reader, writer, null);
	}

	public <T> void register(Class<T> clazz, NBTReader<T> reader, NBTWriter<T> writer, NBTFiller<T> filler)
	{
		classes.add(clazz);
		readers.put(clazz, reader);
		writers.put(clazz, writer);
		fillers.put(clazz, filler);
	}

	@Override
	public boolean canHandle(Type type)
	{
		return classes.contains(type);
	}

	@Override
	public <T> NBTWriter<T> getWriter(NBTMapper parent, Type type)
	{
		return (NBTWriter<T>) writers.get(type);
	}

	@Override
	public <T> NBTReader<T> getReader(NBTMapper parent, Type type)
	{
		return (NBTReader<T>) readers.get(type);
	}

	@Override
	public <T> NBTFiller<T> getFiller(NBTMapper parent, Type type)
	{
		return (NBTFiller<T>) fillers.get(type);
	}
}
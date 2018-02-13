package io.darkcraft.darkcore.nbt.mapper;

import java.lang.reflect.Type;

public interface PartialMapper
{
	public boolean canHandle(Type type);

	public <T> NBTWriter<T> getWriter(NBTMapper parent, Type type);

	public <T> NBTReader<T> getReader(NBTMapper parent, Type type);

	public <T> NBTFiller<T> getFiller(NBTMapper parent, Type type);

	public default <T> NBTWriter<T> getWriter(NBTMapper parent, Class<T> clazz)
	{
		return this.<T>getWriter(parent, clazz);
	}

	public default <T> NBTReader<T> getReader(NBTMapper parent, Class<T> clazz)
	{
		return this.<T>getReader(parent, clazz);
	}

	public default <T> NBTFiller<T> getFiller(NBTMapper parent, Class<T> clazz)
	{
		return this.<T>getFiller(parent, clazz);
	}
}

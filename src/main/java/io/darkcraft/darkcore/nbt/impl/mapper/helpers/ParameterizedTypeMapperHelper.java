package io.darkcraft.darkcore.nbt.impl.mapper.helpers;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.darkcraft.darkcore.nbt.mapper.NBTFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;
import io.darkcraft.darkcore.nbt.mapper.PartialMapper;

public abstract class ParameterizedTypeMapperHelper implements PartialMapper
{

	@Override
	public boolean canHandle(Type type)
	{
		if(type instanceof ParameterizedType)
			return canHandle((ParameterizedType) type);
		return false;
	}

	protected abstract boolean canHandle(ParameterizedType type);

	@Override
	public <T> NBTWriter<T> getWriter(NBTMapper parent, Type type)
	{
		return getWriter(parent, (ParameterizedType) type);
	}

	protected abstract <T> NBTWriter<T> getWriter(NBTMapper parent, ParameterizedType type);

	@Override
	public <T> NBTReader<T> getReader(NBTMapper parent, Type type)
	{
		return getReader(parent, (ParameterizedType) type);
	}

	protected abstract <T> NBTReader<T> getReader(NBTMapper parent, ParameterizedType type);

	@Override
	public <T> NBTFiller<T> getFiller(NBTMapper parent, Type type)
	{
		return getFiller(parent, (ParameterizedType) type);
	}

	protected abstract <T> NBTFiller<T> getFiller(NBTMapper parent, ParameterizedType type);

}

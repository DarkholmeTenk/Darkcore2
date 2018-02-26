package io.darkcraft.darkcore.nbt.impl.wrappers.array;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.darkcraft.darkcore.nbt.mapper.NBTFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;
import io.darkcraft.darkcore.nbt.mapper.PartialMapper;

public class ArrayMapperImpl implements PartialMapper
{

	@Override
	public boolean canHandle(Type type)
	{
		if(type instanceof GenericArrayType)
			return true;
		if(type instanceof Class)
		{
			Class<?> clz = (Class<?>) type;
			if(clz.isArray())
				return true;
		}
		return false;
	}

	private Type getSubType(Type type)
	{
		if(type instanceof GenericArrayType)
			return ((GenericArrayType)type).getGenericComponentType();
		if(type instanceof Class)
		{
			Class<?> clz = (Class<?>) type;
			if(clz.isArray())
				return clz.getComponentType();
		}
		return null;
	}

	private Class<?> getSubClass(Type type)
	{
		if(type instanceof Class)
		{
			return ((Class) type).getComponentType();
		}
		else
		{
			Type t = ((GenericArrayType)type).getGenericComponentType();
			if(t instanceof GenericArrayType)
				return Array.newInstance(getSubClass(t), 0).getClass();
			else if(t instanceof ParameterizedType)
				return (Class<?>) ((ParameterizedType)t).getRawType();
			return null;
		}
	}

	@Override
	public <T> NBTWriter<T> getWriter(NBTMapper parent, Type type)
	{
		Type child = getSubType(type);
		if(child == null)
			return null;
		NBTWriter<?> childWriter = parent.getWriter(child);
		return (NBTWriter<T>) new ArrayWriter<>(childWriter);
	}

	private <T> NBTReader<T[]> getReaderInt(NBTMapper parent, Type type)
	{
		Type child = getSubType(type);
		Class<T> clz = (Class<T>) getSubClass(type);
		if((child == null) || (clz == null))
			return null;
		NBTReader<T> reader = parent.<T>getReader(child);
		return new ArrayReader<>(reader, clz);
	}

	@Override
	public <T> NBTReader<T> getReader(NBTMapper parent, Type type)
	{
		return (NBTReader<T>) getReaderInt(parent, type);
	}

	private <T> NBTFiller<T[]> getFillerInt(NBTMapper parent, Type type)
	{
		Type child = getSubType(type);
		if(child == null)
			return null;
		NBTReader<T> reader = parent.<T>getReader(child);
		NBTFiller<T> filler = parent.<T>getFiller(child);
		return new ArrayFiller<>(reader, filler);
	}

	@Override
	public <T> NBTFiller<T> getFiller(NBTMapper parent, Type type)
	{
		return (NBTFiller<T>) getFillerInt(parent, type);
	}

}

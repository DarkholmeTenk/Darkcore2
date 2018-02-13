package io.darkcraft.darkcore.nbt.impl.wrappers.collection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import io.darkcraft.darkcore.nbt.mapper.NBTFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;
import io.darkcraft.darkcore.nbt.mapper.PartialMapper;

public class CollectionMapperImpl implements PartialMapper
{
	@Override
	public boolean canHandle(Type type)
	{
		if(type instanceof ParameterizedType)
		{
			ParameterizedType pType = (ParameterizedType) type;
			Type rawType = pType.getRawType();
			if(rawType instanceof Class)
			{
				Class<?> raw = (Class<?>) rawType;
				if(Collection.class.isAssignableFrom(raw))
					return true;
			}
		}
		return false;
	}

	@Override
	public <T> NBTWriter<T> getWriter(NBTMapper parent, Type type)
	{
		ParameterizedType pType = (ParameterizedType) type;
		Type child = pType.getActualTypeArguments()[0];
		NBTWriter<?> childWriter = parent.getWriter(child);
		return (NBTWriter<T>) new CollectionWriter<>(childWriter);
	}

	@Override
	public <T> NBTReader<T> getReader(NBTMapper parent, Type type)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> NBTFiller<T> getFiller(NBTMapper parent, Type type)
	{
		// TODO Auto-generated method stub
		return null;
	}
}

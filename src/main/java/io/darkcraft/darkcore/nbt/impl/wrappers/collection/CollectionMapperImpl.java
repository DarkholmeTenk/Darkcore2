package io.darkcraft.darkcore.nbt.impl.wrappers.collection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;

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

	private <T> Supplier<Collection<T>> getSupplier(ParameterizedType pType)
	{
		Type raw = pType.getRawType();
		if((raw == Collection.class) || (raw == List.class) || (raw == ArrayList.class))
			return ArrayList::new;
		if((raw == Queue.class) || (raw == Deque.class) || (raw == LinkedList.class))
			return LinkedList::new;
		if((raw == Set.class) || (raw == HashSet.class))
			return HashSet::new;
		if(raw == TreeSet.class)
			return TreeSet::new;

		throw new IllegalStateException("Unable to produce supplier for " + pType);
	}

	@Override
	public <T> NBTReader<T> getReader(NBTMapper parent, Type type)
	{
		ParameterizedType pType = (ParameterizedType) type;
		Type child = pType.getActualTypeArguments()[0];
		NBTReader<?> childReader = parent.getReader(child);
		return (NBTReader<T>) new CollectionBaseReader<>(childReader, getSupplier(pType));
	}

	@Override
	public <T> NBTFiller<T> getFiller(NBTMapper parent, Type type)
	{
		// TODO Auto-generated method stub
		return null;
	}
}

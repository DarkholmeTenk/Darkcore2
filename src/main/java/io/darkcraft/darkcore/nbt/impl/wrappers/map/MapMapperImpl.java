package io.darkcraft.darkcore.nbt.impl.wrappers.map;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;

import io.darkcraft.darkcore.nbt.impl.mapper.helpers.ParameterizedTypeMapperHelper;
import io.darkcraft.darkcore.nbt.mapper.NBTFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;

import gnu.trove.map.hash.THashMap;

public class MapMapperImpl extends ParameterizedTypeMapperHelper
{

	@Override
	protected boolean canHandle(ParameterizedType type)
	{
		Type base = type.getRawType();
		if((base instanceof Class) && Map.class.isAssignableFrom((Class<?>) base))
			return true;
		return false;
	}

	@Override
	protected <T> NBTWriter<T> getWriter(NBTMapper parent, ParameterizedType type)
	{
		NBTWriter<?> key = parent.getWriter(type.getActualTypeArguments()[0]);
		NBTWriter<?> val = parent.getWriter(type.getActualTypeArguments()[1]);
		return (NBTWriter<T>) new MapWriter<>(key, val);
	}

	private <K,V> Supplier<Map<K,V>> getSupplier(Type raw)
	{
		if(raw == HashMap.class)
			return HashMap::new;
		if(raw == TreeMap.class)
			return TreeMap::new;
		if(raw == THashMap.class)
			return THashMap::new;
		return null;
	}

	private boolean canSupply(Type t)
	{
		return getSupplier(t) != null;
	}

	@Override
	protected <T> NBTReader<T> getReader(NBTMapper parent, ParameterizedType type)
	{
		NBTReader<?> key = parent.getReader(type.getActualTypeArguments()[0]);
		NBTReader<?> val = parent.getReader(type.getActualTypeArguments()[1]);
		if(canSupply(type.getRawType()))
			return (NBTReader<T>) new MapBaseReader<>(key, val, getSupplier(type.getRawType()));
		return null;
	}

	private <T,K,V> NBTFiller<T> getFillerImpl(NBTMapper parent, ParameterizedType type)
	{
		NBTReader<K> key = parent.getReader(type.getActualTypeArguments()[0]);
		NBTReader<V> val = parent.getReader(type.getActualTypeArguments()[1]);
		NBTFiller<V> valFiller = parent.getFiller(type.getActualTypeArguments()[1]);
		return (NBTFiller<T>) new MapFiller<>(key, val, valFiller);
	}

	@Override
	protected <T> NBTFiller<T> getFiller(NBTMapper parent, ParameterizedType type)
	{
		return getFillerImpl(parent, type);
	}
}

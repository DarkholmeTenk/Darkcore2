package io.darkcraft.darkcore.nbt.impl.subtype;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import io.darkcraft.darkcore.nbt.annot.NBTSubTypes;
import io.darkcraft.darkcore.nbt.impl.reflection.ReflectionMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;
import io.darkcraft.darkcore.nbt.mapper.PartialMapper;
import io.darkcraft.darkcore.nbt.util.ReflectHelper;

public class SubTypeMapper implements PartialMapper
{
	private final ReflectionMapper refMapper;
	private final Map<Class<?>, SubTypeMapperWrapper<?>> wrappers;

	public SubTypeMapper(ReflectionMapper refMapper)
	{
		this.refMapper = refMapper;
		wrappers = new HashMap<>();
	}

	@Override
	public boolean canHandle(Type type)
	{
		return ReflectHelper.getBaseClass(type).isAnnotationPresent(NBTSubTypes.class);
	}

	private <T> SubTypeMapperWrapper<T> getWrapper(NBTMapper mapper, Type t)
	{
		Class<T> clz = (Class<T>) ReflectHelper.getBaseClass(t);
		return (SubTypeMapperWrapper<T>)
				wrappers.computeIfAbsent(clz, c->SubTypeMapperWrapper.<T>construct(mapper, refMapper, t));
	}

	@Override
	public <T> NBTWriter<T> getWriter(NBTMapper parent, Type type)
	{
		return getWrapper(parent, type);
	}

	@Override
	public <T> NBTReader<T> getReader(NBTMapper parent, Type type)
	{
		return getWrapper(parent, type);
	}

	@Override
	public <T> NBTFiller<T> getFiller(NBTMapper parent, Type type)
	{
		return getWrapper(parent, type);
	}
}
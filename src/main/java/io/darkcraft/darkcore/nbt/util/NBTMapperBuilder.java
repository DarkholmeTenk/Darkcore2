package io.darkcraft.darkcore.nbt.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.darkcraft.darkcore.nbt.impl.mapper.NBTMapperImpl;
import io.darkcraft.darkcore.nbt.impl.mapper.NBTMapperViewImpl;
import io.darkcraft.darkcore.nbt.impl.reflection.ReflectionMapper;
import io.darkcraft.darkcore.nbt.impl.subtype.SubTypeMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.PartialMapper;

public class NBTMapperBuilder
{
	private final List<PartialMapper> partialMappers;

	private Class<?> viewClazz;

	NBTMapperBuilder(
			List<PartialMapper> partialMappers)
	{
		this.partialMappers = new ArrayList<>(partialMappers);
	}

	public NBTMapperBuilder withPartialMappers(PartialMapper... mappers)
	{
		partialMappers.addAll(Arrays.asList(mappers));
		return this;
	}

	public NBTMapperBuilder withView(Class<?> viewClazz)
	{
		this.viewClazz = viewClazz;
		return this;
	}

	public NBTMapper build()
	{
		ReflectionMapper rm = new ReflectionMapper();
		SubTypeMapper st = new SubTypeMapper(rm);
		partialMappers.add(st);
		partialMappers.add(rm);
		if(viewClazz == null)
			return new NBTMapperImpl(partialMappers);
		else
			return new NBTMapperViewImpl(partialMappers, viewClazz);
	}
}

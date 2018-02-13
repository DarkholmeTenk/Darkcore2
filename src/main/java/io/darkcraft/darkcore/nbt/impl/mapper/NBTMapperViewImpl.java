package io.darkcraft.darkcore.nbt.impl.mapper;

import java.util.List;

import io.darkcraft.darkcore.nbt.mapper.PartialMapper;

public class NBTMapperViewImpl extends NBTMapperImpl
{

	public NBTMapperViewImpl(List<PartialMapper> partialMappers,
			Class<?> viewClazz)
	{
		super(partialMappers);
	}

}

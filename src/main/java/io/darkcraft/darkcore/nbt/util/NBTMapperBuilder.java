package io.darkcraft.darkcore.nbt.util;

import java.util.List;

import io.darkcraft.darkcore.nbt.impl.mapper.NBTMapperImpl;
import io.darkcraft.darkcore.nbt.impl.mapper.NBTMapperViewImpl;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.PartialMapper;

public class NBTMapperBuilder
{
	private final List<PartialMapper> partialMappers;

	private Class<?> viewClazz;

	NBTMapperBuilder(
			List<PartialMapper> partialMappers)
	{
		this.partialMappers = partialMappers;
	}

	public NBTMapperBuilder withView(Class<?> viewClazz)
	{
		this.viewClazz = viewClazz;
		return this;
	}

	public NBTMapper build()
	{
		if(viewClazz == null)
			return new NBTMapperImpl(partialMappers);
		else
			return new NBTMapperViewImpl(partialMappers, viewClazz);
	}
}

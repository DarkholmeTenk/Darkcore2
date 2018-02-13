package io.darkcraft.darkcore.nbt.impl.mapper;

import java.lang.reflect.Type;
import java.util.List;

import io.darkcraft.darkcore.nbt.mapper.NBTFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;
import io.darkcraft.darkcore.nbt.mapper.PartialMapper;

public class NBTMapperImpl implements NBTMapper
{
	private final List<PartialMapper> partialMappers;

	public NBTMapperImpl(List<PartialMapper> partialMappers)
	{
		this.partialMappers = partialMappers;
	}

	@Override
	public <T> NBTWriter<T> getWriter(Type type)
	{
		for(PartialMapper mapper : partialMappers)
			if(mapper.canHandle(type))
				return mapper.getWriter(this, type);
		throw new RuntimeException("No mapper");
	}

	@Override
	public <T> NBTReader<T> getReader(Type type)
	{
		for(PartialMapper mapper : partialMappers)
			if(mapper.canHandle(type))
				return mapper.getReader(this, type);
		throw new RuntimeException("No mapper");
	}

	@Override
	public <T> NBTFiller<T> getFiller(Type type)
	{
		for(PartialMapper mapper : partialMappers)
			if(mapper.canHandle(type))
				return mapper.getFiller(this, type);
		throw new RuntimeException("No mapper");
	}

}

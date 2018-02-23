package io.darkcraft.darkcore.nbt.impl.mapper;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

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

	private <U> U findU(Type type, Function<PartialMapper,U> function)
	{
		return partialMappers.stream()
				.filter(mapper->mapper.canHandle(type))
				.map(function)
				.filter(Objects::nonNull)
				.findFirst()
				.orElse(null);
	}

	@Override
	public <T> NBTWriter<T> getWriter(Type type)
	{
		return findU(type, mapper->mapper.getWriter(this, type));
	}

	@Override
	public <T> NBTReader<T> getReader(Type type)
	{
		return findU(type, mapper->mapper.getReader(this, type));
	}

	@Override
	public <T> NBTFiller<T> getFiller(Type type)
	{
		return findU(type, mapper->mapper.getFiller(this, type));
	}

}

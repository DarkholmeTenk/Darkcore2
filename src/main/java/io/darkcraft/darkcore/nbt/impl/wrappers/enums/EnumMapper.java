package io.darkcraft.darkcore.nbt.impl.wrappers.enums;

import java.lang.reflect.Type;

import io.darkcraft.darkcore.nbt.mapper.NBTFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;
import io.darkcraft.darkcore.nbt.mapper.PartialMapper;

public class EnumMapper implements PartialMapper
{

	@Override
	public boolean canHandle(Type type)
	{
		if(type instanceof Class)
			return Enum.class.isAssignableFrom((Class<?>) type);
		return false;
	}

	@Override
	public <T> NBTWriter<T> getWriter(NBTMapper parent, Type type)
	{
		return (NBTWriter<T>) new EnumWriter<>();
	}

	private <T extends Enum<T>> NBTReader<T> getReader(Class<?> clz)
	{
		return new EnumReader<>((Class<T>) clz);
	}

	@Override
	public <T> NBTReader<T> getReader(NBTMapper parent, Type type)
	{
		Class<T> clz = (Class<T>) type;
		return (NBTReader<T>) getReader(clz);
	}

	@Override
	public <T> NBTFiller<T> getFiller(NBTMapper parent, Type type)
	{
		// TODO Auto-generated method stub
		return null;
	}

}

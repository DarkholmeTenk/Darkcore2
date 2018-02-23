package io.darkcraft.darkcore.nbt.impl.wrappers.array;

import java.lang.reflect.Type;

import io.darkcraft.darkcore.nbt.mapper.NBTFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;
import io.darkcraft.darkcore.nbt.mapper.PartialMapper;

public class ArrayMapperImpl implements PartialMapper
{

	@Override
	public boolean canHandle(Type type)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> NBTWriter<T> getWriter(NBTMapper parent, Type type)
	{
		// TODO Auto-generated method stub
		return null;
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

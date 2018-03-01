package io.darkcraft.darkcore.nbt.impl.reflection.reading;

import java.lang.reflect.Parameter;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.annot.NBTProperty;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.util.ReflectHelper;

class ReadingConstructorParam<V>
{
	private final String name;
	private final NBTReader<V> reader;

	ReadingConstructorParam(String name, NBTReader<V> reader)
	{
		this.name = name;
		this.reader = reader;
	}

	public String getName()
	{
		return name;
	}

	public Object getValue(NBTTagCompound nbt)
	{
		return reader.readFromNBT(nbt, name);
	}

	static ReadingConstructorParam<?> getParam(NBTMapper parent, Parameter param)
	{
		String name = ReflectHelper.getAnnotation(param, NBTProperty.class)
				.map(NBTProperty::value)
				.orElseGet(param::getName);
		NBTReader<?> reader = parent.getReader(param.getParameterizedType());
		return new ReadingConstructorParam(name, reader);
	}
}

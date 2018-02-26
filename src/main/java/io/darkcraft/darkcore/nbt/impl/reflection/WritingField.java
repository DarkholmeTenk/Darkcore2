package io.darkcraft.darkcore.nbt.impl.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.exception.NBTMapperBuildException;
import io.darkcraft.darkcore.nbt.exception.NBTMappingException;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;

class WritingField<T,V>
{
	private static final Logger LOGGER = LogManager.getLogger(WritingField.class);

	Function<T, V> field;
	NBTWriter<V> writer;

	WritingField(Function<T, V> field, NBTWriter<V> writer)
	{
		this.field = field;
		this.writer = writer;
	}

	void write(NBTTagCompound nbt, String id, T t)
	{
		writer.writeToNBT(nbt, id, field.apply(t));
	}

	static <T, V> WritingField<T,V> getField(NBTMapper parent, Field f)
	{
		Function<T, V> map = t->{
			try
			{
				return (V) f.get(t);
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				LOGGER.catching(e);
				throw new NBTMappingException("Unable to serialize for " + f, e);
			}
		};
		NBTWriter<V> writer = parent.<V>getWriter(f.getGenericType());
		if(writer == null)
			throw new NBTMapperBuildException("Unable to find writer for " + f.getGenericType());
		return new WritingField<>(map, writer);
	}

	static <T, V> WritingField<T,V> getField(NBTMapper parent, Method m)
	{
		Function<T, V> map = t->{
			try
			{
				return (V) m.invoke(t);
			}
			catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e)
			{
				LOGGER.catching(e);
				throw new NBTMappingException("Unable to serialize for " + m, e);
			}
		};
		NBTWriter<V> writer = parent.<V>getWriter(m.getGenericReturnType());
		if(writer == null)
			throw new NBTMapperBuildException("Unable to find writer for " + m.getGenericReturnType());
		return new WritingField<>(map, writer);
	}
}
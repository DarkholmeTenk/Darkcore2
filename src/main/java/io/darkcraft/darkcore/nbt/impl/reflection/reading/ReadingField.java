package io.darkcraft.darkcore.nbt.impl.reflection.reading;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.exception.NBTMapperBuildException;
import io.darkcraft.darkcore.nbt.exception.NBTMappingException;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;

class ReadingField<T,V>
{
	private static final Logger LOGGER = LogManager.getLogger(ReadingField.class);

	private final BiConsumer<T,V> value;
	private final NBTReader<V> reader;

	private ReadingField(BiConsumer<T,V> value, NBTReader<V> reader)
	{
		this.value = value;
		this.reader = reader;
	}

	public void read(NBTTagCompound nbt, String id, T obj)
	{
		value.accept(obj, reader.readFromNBT(nbt, id));
	}

	public static <T,V> ReadingField<T,V> getField(NBTMapper parent, Field f)
	{
		BiConsumer<T,V> consumer = (t,v)->{
			try
			{
				f.set(t, v);
			}
			catch(IllegalArgumentException | IllegalAccessException e)
			{
				LOGGER.catching(e);
				throw new NBTMappingException("Unable to deserialize for " + f, e);
			}
		};
		NBTReader<V> reader = parent.getReader(f.getGenericType());
		if(reader == null)
			throw new NBTMapperBuildException("Unable to find reader for " + f.getGenericType());
		return new ReadingField<>(consumer, reader);
	}

	public static <T,V> ReadingField<T,V> getField(NBTMapper parent, Method m)
	{
		BiConsumer<T,V> consumer = (t,v)->{
			try
			{
				m.invoke(t, v);
			}
			catch(IllegalArgumentException | IllegalAccessException | InvocationTargetException e)
			{
				LOGGER.catching(e);
				throw new NBTMappingException("Unable to deserialize for " + m, e);
			}
		};
		NBTReader<V> reader = parent.getReader(m.getGenericParameterTypes()[0]);
		if(reader == null)
			throw new NBTMapperBuildException("Unable to find reader for " + m.getGenericParameterTypes()[0]);
		return new ReadingField<>(consumer, reader);
	}
}

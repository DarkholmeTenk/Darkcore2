package io.darkcraft.darkcore.nbt.impl.reflection.reading;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
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

	final Type type;
	final BiConsumer<T,V> value;
	final NBTReader<V> reader;

	private ReadingField(Type type, BiConsumer<T,V> value, NBTReader<V> reader)
	{
		this.type = type;
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
		Type t = f.getGenericType();
		NBTReader<V> reader = parent.getReader(t);
		if(reader == null)
			throw new NBTMapperBuildException("Unable to find reader for " + t);
		return new ReadingField<>(t, consumer, reader);
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
		Type t = m.getGenericParameterTypes()[0];
		NBTReader<V> reader = parent.getReader(t);
		if(reader == null)
			throw new NBTMapperBuildException("Unable to find reader for " + t);
		return new ReadingField<>(t, consumer, reader);
	}
}

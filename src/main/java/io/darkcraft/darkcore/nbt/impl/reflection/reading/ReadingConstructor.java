package io.darkcraft.darkcore.nbt.impl.reflection.reading;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.annot.NBTConstructor;
import io.darkcraft.darkcore.nbt.exception.NBTMapperBuildException;
import io.darkcraft.darkcore.nbt.exception.NBTReadingException;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;

class ReadingConstructor<T>
{
	private static final Logger LOGGER = LogManager.getLogger(ReadingConstructor.class);

	private final Constructor<T> constructor;
	private final List<ReadingConstructorParam<?>> params;

	private ReadingConstructor(Constructor<T> constructor, List<ReadingConstructorParam<?>> params)
	{
		this.constructor = constructor;
		this.params = params;
	}

	public T readFromNBT(NBTTagCompound nbt)
	{
		try
		{
			return constructor.newInstance(params.stream()
					.map(param->param.getValue(nbt))
					.toArray());
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			LOGGER.error("Unable to construct " + constructor, e);
			throw new NBTReadingException("Unable to constructo " + constructor, e);
		}
	}

	Set<String> getFields()
	{
		return params.stream()
				.map(ReadingConstructorParam::getName)
				.collect(Collectors.toSet());
	}

	private static <T> ReadingConstructor<T> construct(NBTMapper parent, Class<T> baseClass,
			Constructor<T> constructor)
	{
		int modifiers = constructor.getModifiers();
		if(Modifier.isPrivate(modifiers))
			throw new NBTMapperBuildException("Can't use private constructors: " + constructor);
		List<ReadingConstructorParam<?>> params = Arrays.stream(constructor.getParameters())
				.map(type->ReadingConstructorParam.getParam(parent, type))
				.collect(Collectors.toList());
		return new ReadingConstructor<>(constructor, params);
	}

	static <T> ReadingConstructor<T> construct(NBTMapper parent, Class<T> baseClass)
	{
		for(Constructor<?> constructor : baseClass.getDeclaredConstructors())
		{
			if(!constructor.isAnnotationPresent(NBTConstructor.class))
				continue;
			return construct(parent, baseClass, (Constructor<T>) constructor);
		}
		try
		{
			return construct(parent, baseClass, baseClass.getDeclaredConstructor());
		}
		catch(NoSuchMethodException e)
		{
			throw new NBTMapperBuildException("Couldn't find a @NBTConstructor constructor or no-args constructor", e);
		}
	}
}

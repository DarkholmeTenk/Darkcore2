package io.darkcraft.darkcore.nbt.impl.reflection;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import io.darkcraft.darkcore.nbt.annot.NBTReflect;
import io.darkcraft.darkcore.nbt.impl.reflection.reading.ReflectionFiller;
import io.darkcraft.darkcore.nbt.impl.reflection.reading.ReflectionReader;
import io.darkcraft.darkcore.nbt.impl.reflection.reading.ReflectionWriter;
import io.darkcraft.darkcore.nbt.mapper.NBTFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;
import io.darkcraft.darkcore.nbt.mapper.PartialMapper;
import io.darkcraft.darkcore.nbt.util.ReflectHelper;

public class ReflectionMapper implements PartialMapper
{
	private final Map<Type, NBTWriter<?>> writers = new HashMap<>();
	private final Map<Type, NBTReader<?>> readers = new HashMap<>();
	private final Map<Type, NBTFiller<?>> fillers = new HashMap<>();

	@Override
	public boolean canHandle(Type type)
	{
		Class<?> baseClass = ReflectHelper.getBaseClass(type);
		if(baseClass == null)
			return false;
		return baseClass.isAnnotationPresent(NBTReflect.class);
	}

	@Override
	public <T> NBTWriter<T> getWriter(NBTMapper parent, Type type)
	{
		return (NBTWriter<T>) writers.computeIfAbsent(type,
				t->ReflectionWriter.construct(parent, ReflectHelper.getBaseClass(t)));
	}

	@Override
	public <T> NBTReader<T> getReader(NBTMapper parent, Type type)
	{
		return (NBTReader<T>) readers.computeIfAbsent(type,
				t->ReflectionReader.construct(parent, ReflectHelper.getBaseClass(t)));
	}

	@Override
	public <T> NBTFiller<T> getFiller(NBTMapper parent, Type type)
	{
		return (NBTFiller<T>) fillers.computeIfAbsent(type,
				t->ReflectionFiller.construct(parent, ReflectHelper.getBaseClass(t)));
	}

}

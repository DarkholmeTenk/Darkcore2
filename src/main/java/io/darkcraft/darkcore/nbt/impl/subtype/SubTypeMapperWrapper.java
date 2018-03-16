package io.darkcraft.darkcore.nbt.impl.subtype;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.annot.NBTSubTypes;
import io.darkcraft.darkcore.nbt.annot.NBTSubTypes.SubType;
import io.darkcraft.darkcore.nbt.impl.reflection.ReflectionMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTFiller.NBTObjFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader.NBTObjReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter.NBTObjWriter;
import io.darkcraft.darkcore.nbt.util.ReflectHelper;

public class SubTypeMapperWrapper<T> implements NBTObjReader<T>, NBTObjFiller<T>, NBTObjWriter<T>
{
	private final Type original;
	private final NBTMapper mapper;

	private final String fieldName;
	private final Class<? extends T> defaultClass;
	private final BiMap<String, Class<? extends T>> nameMap;
	private final Map<Class<? extends T>, SubTypeMapperContainer<? extends T>> containers;

	SubTypeMapperWrapper(Type original, NBTMapper mapper,
			String fieldName, Class<? extends T> defaultClass, BiMap<String, Class<? extends T>> nameMap)
	{
		this.original = original;
		this.mapper = mapper;
		this.fieldName = fieldName;
		this.defaultClass = defaultClass;
		this.nameMap = nameMap;
		this.containers = new HashMap<>();
	}

	private <V extends T> SubTypeMapperContainer<V> getContainer(Class<V> clz)
	{
		return (SubTypeMapperContainer<V>)
				containers.computeIfAbsent(clz, cl->SubTypeMapperContainer.construct(cl, mapper));
	}

	@Override
	public T readFromNBT(NBTTagCompound nbt)
	{
		Class<? extends T> clz;
		if(nbt.hasKey(fieldName))
			clz = nameMap.get(nbt.getString(fieldName));
		else
			clz = defaultClass;
		Preconditions.checkNotNull(clz, "Unable to find class for " + nbt + ": " + original);
		return getContainer(clz).readFromNBT(nbt);
	}

	private <V extends T> void writeInternal(NBTTagCompound nbt, V value)
	{
		Class<V> clz = (Class<V>) value.getClass();
		if(clz != defaultClass)
		{
			String type = nameMap.inverse().get(clz);
			Preconditions.checkNotNull(type, "Unable to find name for " + clz + " with original type " + original);
			nbt.setString(fieldName, type);
		}
		getContainer(clz).writeToNBT(nbt, value);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, T value)
	{
		writeInternal(nbt, value);
	}

	private <V extends T> void fillInternal(NBTTagCompound nbt, V value)
	{
		Class<V> clz = (Class<V>) value.getClass();
		Class<?> writtenClass = nameMap.get(nbt.getString(fieldName));
		Preconditions.checkArgument(((writtenClass == null) && (clz == defaultClass)) || (clz == writtenClass),
				"Unable to find class " + clz + " = " + writtenClass + " for " + original);
		getContainer(clz).fillFromNBT(nbt, value);
	}

	@Override
	public void fillFromNBT(NBTTagCompound nbt, T existing)
	{
		fillInternal(nbt, existing);
	}

	private <V extends T> boolean isValidInternal(NBTTagCompound nbt, V value)
	{
		Class<V> clz = (Class<V>) value.getClass();
		Class<?> writtenClass = nameMap.get(nbt.getString(fieldName));
		if(writtenClass == null)
			writtenClass = defaultClass;
		if(clz == defaultClass)
			return getContainer(clz).isValid(nbt, value);
		else
			return false;
	}

	@Override
	public boolean isValid(NBTTagCompound nbt, T existing)
	{
		return isValidInternal(nbt, existing);
	}

	public static <T> SubTypeMapperWrapper<T> construct(NBTMapper mapper, ReflectionMapper refMapper,
			Type type)
	{
		Class<T> base = (Class<T>) ReflectHelper.getBaseClass(type);
		NBTSubTypes subTypes = base.getAnnotation(NBTSubTypes.class);
		Class<? extends T> defaultType = (Class<? extends T>) subTypes.defaultClass();
		if(defaultType == Object.class)
			defaultType = null;
		BiMap<String, Class<?>> nameMap = HashBiMap.create();
		for(SubType st : subTypes.value())
			nameMap.put(st.name(), st.clazz());
		SubTypeMapperWrapper wrapper = new SubTypeMapperWrapper(type, mapper, subTypes.fieldName(), defaultType, nameMap);
		if((defaultType == base) && refMapper.canHandle(base))
		{
			wrapper.containers.put(defaultType, new SubTypeMapperContainer<>(
					refMapper.getReader(mapper, base),
					refMapper.getWriter(mapper, base),
					refMapper.getFiller(mapper, base)));
		}
		return wrapper;
	}
}

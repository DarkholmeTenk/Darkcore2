package io.darkcraft.darkcore.nbt.mapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.darkcraft.darkcore.nbt.annot.NBTView;
import io.darkcraft.darkcore.nbt.impl.wrappers.PolymorphicWrapper;
import io.darkcraft.darkcore.nbt.impl.wrappers.collection.CollectionBaseReader;
import io.darkcraft.darkcore.nbt.util.TypeKey;

/**
 * An initialised mapper which should be capable of producing readers and writers.<p/>
 *
 * May have a view class as described in {@link NBTView} or may be pure.
 *
 * @author dark
 *
 */
public interface NBTMapper
{
	/**
	 * Returns a writer for the specified class
	 * @param type the class to produce a writer capable of serializing
	 * @return a writer for writing data to a tag
	 */
	public <T> NBTWriter<T> getWriter(Type type);

	public <T> NBTReader<T> getReader(Type clazz);

	public <T> NBTFiller<T> getFiller(Type clazz);

	public default <T> NBTWriter<T> getWriter(Class<T> clazz)
	{
		return this.<T>getWriter(clazz);
	}

	public default <T> NBTReader<T> getReader(Class<T> clazz)
	{
		return this.<T>getReader(clazz);
	}

	public default <T> NBTFiller<T> getFiller(Class<T> clazz)
	{
		return this.<T>getFiller(clazz);
	}

	public default <T> NBTWriter<T> getWriter(TypeKey<T> key)
	{
		return this.<T>getWriter(key.getType());
	}

	public default <T> NBTReader<T> getReader(TypeKey<T> key)
	{
		return this.<T>getReader(key.getType());
	}

	public default <T> NBTFiller<T> getFiller(TypeKey<T> key)
	{
		return this.<T>getFiller(key.getType());
	}

	public default PolymorphicWrapper getPolymorphic()
	{
		return new PolymorphicWrapper(this);
	}

	public default <T> CollectionBaseReader<T, List<T>> getListReader(Class<T> clazz)
	{
		return new CollectionBaseReader<>(getReader(clazz), ArrayList::new);
	}

	public default Class<?> getViewClass()
	{
		return null;
	}
}

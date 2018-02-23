package io.darkcraft.darkcore.nbt.mapper;

import java.lang.reflect.Type;

public interface PartialMapper
{
	/**
	 * @return true if any of reader, writer or filler can handle type
	 */
	public boolean canHandle(Type type);

	/**
	 * @return a writer which can turn type in to nbt or null if no such writer can be produced
	 */
	public <T> NBTWriter<T> getWriter(NBTMapper parent, Type type);

	/**
	 * @return a reader which can produce type or null if no such reader can be produced
	 */
	public <T> NBTReader<T> getReader(NBTMapper parent, Type type);

	/**
	 * @return a filler which can fill in type or null if no such filler can be produced.
	 */
	public <T> NBTFiller<T> getFiller(NBTMapper parent, Type type);

	public default <T> NBTWriter<T> getWriter(NBTMapper parent, Class<T> clazz)
	{
		return this.<T>getWriter(parent, clazz);
	}

	public default <T> NBTReader<T> getReader(NBTMapper parent, Class<T> clazz)
	{
		return this.<T>getReader(parent, clazz);
	}

	public default <T> NBTFiller<T> getFiller(NBTMapper parent, Class<T> clazz)
	{
		return this.<T>getFiller(parent, clazz);
	}
}

package io.darkcraft.darkcore.nbt.mapper;

import io.darkcraft.darkcore.nbt.annot.NBTView;

/**
 * An initialized mapper which should be capable of producing readers and writers.<p/>
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
	 * @param clazz the class to produce a writer capable of serializing
	 * @return a writer for writing data to a tag
	 */
	public <T> NBTWriter<T> getWriter(Class<T> clazz);

	public <T> NBTReader<T> getReader(Class<T> clazz);
}

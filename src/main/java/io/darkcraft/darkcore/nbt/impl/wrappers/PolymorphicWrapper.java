package io.darkcraft.darkcore.nbt.impl.wrappers;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.exception.NBTReadingException;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTReader.NBTObjReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter.NBTObjWriter;

/**
 * A reader/writer which can handle anything the parent mapper can, but wraps it in a simple
 * object which maintains enough class information that it can be read or written without
 * needing to know the class in advance.
 *
 * @author dark
 *
 */
public class PolymorphicWrapper implements NBTObjWriter<Object>, NBTObjReader<Object>
{
	private static final String CLASS_KEY = "C";
	private static final String VAL_KEY = "V";

	private final NBTMapper mapper;

	public PolymorphicWrapper(NBTMapper mapper)
	{
		this.mapper = mapper;
	}

	@Override
	public Object readFromNBT(NBTTagCompound child)
	{
		String clazzName = child.getString(CLASS_KEY);
		try
		{
			Class<?> clazz = Class.forName(clazzName);
			NBTReader<Object> reader = (NBTReader<Object>) mapper.getReader(clazz);
			return reader.readFromNBT(child, VAL_KEY);
		}
		catch(ClassNotFoundException e)
		{
			throw new NBTReadingException("Unable to find class: " + clazzName, e);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound child, Object value)
	{
		Class<?> clazz = value.getClass();
		NBTWriter<Object> writer = (NBTWriter<Object>) mapper.getWriter(clazz);
		child.setString(CLASS_KEY, clazz.getName());
		writer.writeToNBT(child, VAL_KEY, value);
	}
}

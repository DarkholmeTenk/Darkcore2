package io.darkcraft.darkcore.nbt.impl.reflection.reading;

import static io.darkcraft.darkcore.nbt.impl.reflection.reading.ReflectionReader.getReadingFields;
import static java.util.Collections.emptySet;

import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.mapper.NBTFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;

public class ReflectionFiller<T> implements NBTFiller<T>
{
	private final Map<String, ReadingField<T,?>> readers;
	private ReflectionFiller(Map<String, ReadingField<T,?>> readers)
	{
		this.readers = readers;
	}

	@Override
	public void fillFromNBT(NBTTagCompound nbt, String id, T existing)
	{
		readers.forEach((n,r)->r.read(nbt, n, existing));
	}

	public static <T> ReflectionFiller<T> construct(NBTMapper parent, Class<T> baseClass)
	{
		return new ReflectionFiller(getReadingFields(parent, emptySet(), baseClass));
	}
}

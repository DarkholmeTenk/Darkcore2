package io.darkcraft.darkcore.nbt.impl.reflection.reading;

import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.mapper.NBTFiller.NBTObjFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;

public class ReflectionFiller<T> implements NBTObjFiller<T>
{
	private final Map<String, FillingField<T,?>> readers;
	private ReflectionFiller(Map<String, FillingField<T,?>> readers)
	{
		this.readers = readers;
	}

	@Override
	public void fillFromNBT(NBTTagCompound nbt, T existing)
	{
		readers.forEach((n,r)->r.read(nbt, n, existing));
	}

	public static <T> ReflectionFiller<T> construct(NBTMapper parent, Class<T> baseClass)
	{
		return new ReflectionFiller(FillingField.construct(parent, baseClass));
	}
}

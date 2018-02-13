package io.darkcraft.darkcore.nbt.impl.reflection;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.mapper.NBTWriter;

public class ReflectionWriter<T> implements NBTWriter<T>
{


	@Override
	public void writeToNBT(NBTTagCompound nbt, String key, T value)
	{
	}

}
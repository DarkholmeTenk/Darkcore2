package io.darkcraft.darkcore.nbt.mapper;

import net.minecraft.nbt.NBTTagCompound;

@FunctionalInterface
public interface NBTReader<T>
{
	public T readFromNBT(NBTTagCompound nbt, String id);
}
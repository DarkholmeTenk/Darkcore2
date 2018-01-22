package io.darkcraft.darkcore.nbt.mapper;

import net.minecraft.nbt.NBTTagCompound;

@FunctionalInterface
public interface NBTWriter<T>
{
	public void writeToNBT(NBTTagCompound nbt, String key, T value);
}

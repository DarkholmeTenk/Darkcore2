package io.darkcraft.darkcore.nbt.impl.wrappers.enums;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.mapper.NBTWriter;

public class EnumWriter<T extends Enum<T>> implements NBTWriter<T>
{
	@Override
	public void writeToNBT(NBTTagCompound nbt, String key, T value)
	{
		nbt.setInteger(key, value.ordinal());
	}
}

package io.darkcraft.darkcore.nbt.mapper;

import javax.annotation.Nonnull;

import net.minecraft.nbt.NBTTagCompound;

@FunctionalInterface
public interface NBTWriter<T>
{
	public void writeToNBT(NBTTagCompound nbt, String key, T value);

	public static interface NBTObjWriter<T> extends NBTWriter<T>
	{
		@Override
		public default void writeToNBT(NBTTagCompound nbt, String key, T value)
		{
			if(value != null)
			{
				NBTTagCompound tag = new NBTTagCompound();
				writeToNBT(tag, value);
				nbt.setTag(key, tag);
			}
		}

		public void writeToNBT(NBTTagCompound nbt, @Nonnull T value);
	}
}

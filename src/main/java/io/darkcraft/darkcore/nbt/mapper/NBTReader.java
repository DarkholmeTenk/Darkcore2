package io.darkcraft.darkcore.nbt.mapper;

import net.minecraft.nbt.NBTTagCompound;

@FunctionalInterface
public interface NBTReader<T>
{
	public T readFromNBT(NBTTagCompound nbt, String id);

	public static interface NBTObjReader<T> extends NBTReader<T>
	{
		@Override
		public default T readFromNBT(NBTTagCompound nbt, String id)
		{
			return nbt.hasKey(id) ? readFromNBT(nbt.getCompoundTag(id)) : null;
		}

		public T readFromNBT(NBTTagCompound nbt);
	}
}
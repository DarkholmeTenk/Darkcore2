package io.darkcraft.darkcore.nbt.mapper;

import net.minecraft.nbt.NBTTagCompound;

/**
 * A variant of NBT Reader which has the potential ability to modify an existing object to fill in data from
 * the provided NBT data
 * @author dark
 *
 */
public interface NBTFiller<T>
{
	public void fillFromNBT(NBTTagCompound nbt, String id, T existing);

	public boolean isValid(NBTTagCompound nbt, String id, T existing);

	public static interface NBTObjFiller<T> extends NBTFiller<T>
	{
		@Override
		public default void fillFromNBT(NBTTagCompound nbt, String id, T existing)
		{
			fillFromNBT(nbt.getCompoundTag(id), existing);
		}

		@Override
		public default boolean isValid(NBTTagCompound nbt, String id, T existing)
		{
			return isValid(nbt.getCompoundTag(id), existing);
		}

		public void fillFromNBT(NBTTagCompound nbt, T existing);

		public boolean isValid(NBTTagCompound nbt, T existing);
	}
}
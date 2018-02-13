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
}
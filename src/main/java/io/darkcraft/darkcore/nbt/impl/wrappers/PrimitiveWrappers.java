package io.darkcraft.darkcore.nbt.impl.wrappers;

import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.util.NBTHelper;

public class PrimitiveWrappers
{
	public static NBTHelper addWrappers(NBTHelper nbtHelper)
	{
		nbtHelper.addGlobalWriter(Integer.class, NBTTagCompound::setInteger);
		nbtHelper.addGlobalWriter(Long.class, NBTTagCompound::setLong);
		nbtHelper.addGlobalWriter(Short.class, NBTTagCompound::setShort);
		nbtHelper.addGlobalWriter(String.class, NBTTagCompound::setString);
		nbtHelper.addGlobalWriter(Double.class, NBTTagCompound::setDouble);
		nbtHelper.addGlobalWriter(Float.class, NBTTagCompound::setFloat);
		nbtHelper.addGlobalWriter(Boolean.class, NBTTagCompound::setBoolean);
		nbtHelper.addGlobalWriter(Byte.class, NBTTagCompound::setByte);
		nbtHelper.addGlobalWriter(byte[].class, NBTTagCompound::setByteArray);
		nbtHelper.addGlobalWriter(int[].class, NBTTagCompound::setIntArray);
		nbtHelper.addGlobalWriter(UUID.class, NBTTagCompound::setUniqueId);

		nbtHelper.addGlobalReader(Integer.class, NBTTagCompound::getInteger);
		nbtHelper.addGlobalReader(Long.class, NBTTagCompound::getLong);
		nbtHelper.addGlobalReader(Short.class, NBTTagCompound::getShort);
		nbtHelper.addGlobalReader(String.class, NBTTagCompound::getString);
		nbtHelper.addGlobalReader(Double.class, NBTTagCompound::getDouble);
		nbtHelper.addGlobalReader(Float.class, NBTTagCompound::getFloat);
		nbtHelper.addGlobalReader(Boolean.class, NBTTagCompound::getBoolean);
		nbtHelper.addGlobalReader(Byte.class, NBTTagCompound::getByte);
		nbtHelper.addGlobalReader(byte[].class, NBTTagCompound::getByteArray);
		nbtHelper.addGlobalReader(int[].class, NBTTagCompound::getIntArray);
		nbtHelper.addGlobalReader(UUID.class, NBTTagCompound::getUniqueId);

		return nbtHelper;
	}
}

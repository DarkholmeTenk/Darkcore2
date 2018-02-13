package io.darkcraft.darkcore.nbt.impl.wrappers;

import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.impl.mapper.ClassPartialMapper;
import io.darkcraft.darkcore.nbt.util.NBTHelper;

public class PrimitiveWrappers
{
	public static NBTHelper addWrappers(NBTHelper nbtHelper)
	{
		ClassPartialMapper classMapper = new ClassPartialMapper();
		classMapper.register(Integer.class, NBTTagCompound::getInteger, NBTTagCompound::setInteger);
		classMapper.register(Long.class, NBTTagCompound::getLong, NBTTagCompound::setLong);
		classMapper.register(Short.class, NBTTagCompound::getShort, NBTTagCompound::setShort);
		classMapper.register(String.class, NBTTagCompound::getString, NBTTagCompound::setString);
		classMapper.register(Double.class, NBTTagCompound::getDouble, NBTTagCompound::setDouble);
		classMapper.register(Float.class, NBTTagCompound::getFloat, NBTTagCompound::setFloat);
		classMapper.register(Boolean.class, NBTTagCompound::getBoolean, NBTTagCompound::setBoolean);
		classMapper.register(Byte.class, NBTTagCompound::getByte, NBTTagCompound::setByte);
		classMapper.register(byte[].class, NBTTagCompound::getByteArray, NBTTagCompound::setByteArray);
		classMapper.register(int[].class, NBTTagCompound::getIntArray, NBTTagCompound::setIntArray);
		classMapper.register(UUID.class, NBTTagCompound::getUniqueId, NBTTagCompound::setUniqueId);
		nbtHelper.addPartialMapper(classMapper);
		return nbtHelper;
	}
}

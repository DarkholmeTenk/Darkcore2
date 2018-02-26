package io.darkcraft.darkcore.nbt.impl.wrappers;

import java.util.UUID;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.impl.mapper.helpers.ClassPartialMapper;
import io.darkcraft.darkcore.nbt.mapper.PartialMapper;

public class PrimitiveWrappers
{
	public static final PartialMapper PARTIAL = new ClassPartialMapper()
			.register(Integer.class, NBTTagCompound::getInteger, NBTTagCompound::setInteger)
			.register(int.class, NBTTagCompound::getInteger, NBTTagCompound::setInteger)
			.register(Long.class, NBTTagCompound::getLong, NBTTagCompound::setLong)
			.register(long.class, NBTTagCompound::getLong, NBTTagCompound::setLong)
			.register(Short.class, NBTTagCompound::getShort, NBTTagCompound::setShort)
			.register(short.class, NBTTagCompound::getShort, NBTTagCompound::setShort)
			.register(String.class, NBTTagCompound::getString, NBTTagCompound::setString)
			.register(Double.class, NBTTagCompound::getDouble, NBTTagCompound::setDouble)
			.register(double.class, NBTTagCompound::getDouble, NBTTagCompound::setDouble)
			.register(Float.class, NBTTagCompound::getFloat, NBTTagCompound::setFloat)
			.register(float.class, NBTTagCompound::getFloat, NBTTagCompound::setFloat)
			.register(Boolean.class, NBTTagCompound::getBoolean, NBTTagCompound::setBoolean)
			.register(boolean.class, NBTTagCompound::getBoolean, NBTTagCompound::setBoolean)
			.register(Byte.class, NBTTagCompound::getByte, NBTTagCompound::setByte)
			.register(byte.class, NBTTagCompound::getByte, NBTTagCompound::setByte)
			.register(byte[].class, NBTTagCompound::getByteArray, NBTTagCompound::setByteArray)
			.register(int[].class, NBTTagCompound::getIntArray, NBTTagCompound::setIntArray)
			.register(UUID.class, NBTTagCompound::getUniqueId, NBTTagCompound::setUniqueId)
			.register(NBTTagCompound.class, NBTTagCompound::getCompoundTag, NBTTagCompound::setTag)
			.register(NBTBase.class, NBTTagCompound::getTag, NBTTagCompound::setTag)
			.finish();
}

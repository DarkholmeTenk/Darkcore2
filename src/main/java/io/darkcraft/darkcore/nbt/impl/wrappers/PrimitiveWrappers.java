package io.darkcraft.darkcore.nbt.impl.wrappers;

import java.util.UUID;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.impl.mapper.helpers.ClassPartialMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;
import io.darkcraft.darkcore.nbt.mapper.PartialMapper;

public class PrimitiveWrappers
{
	public static final PartialMapper PARTIAL = new ClassPartialMapper()
			.register(Integer.class, wrap(NBTTagCompound::getInteger), wrap(NBTTagCompound::setInteger))
			.register(int.class, NBTTagCompound::getInteger, NBTTagCompound::setInteger)
			.register(Long.class, wrap(NBTTagCompound::getLong), wrap(NBTTagCompound::setLong))
			.register(long.class, NBTTagCompound::getLong, NBTTagCompound::setLong)
			.register(Short.class, wrap(NBTTagCompound::getShort), wrap(NBTTagCompound::setShort))
			.register(short.class, NBTTagCompound::getShort, NBTTagCompound::setShort)
			.register(String.class, wrap(NBTTagCompound::getString), wrap(NBTTagCompound::setString))
			.register(Double.class, wrap(NBTTagCompound::getDouble), wrap(NBTTagCompound::setDouble))
			.register(double.class, NBTTagCompound::getDouble, NBTTagCompound::setDouble)
			.register(Float.class, wrap(NBTTagCompound::getFloat), wrap(NBTTagCompound::setFloat))
			.register(float.class, NBTTagCompound::getFloat, NBTTagCompound::setFloat)
			.register(Boolean.class, wrap(NBTTagCompound::getBoolean), wrap(NBTTagCompound::setBoolean))
			.register(boolean.class, NBTTagCompound::getBoolean, NBTTagCompound::setBoolean)
			.register(Byte.class, wrap(NBTTagCompound::getByte), wrap(NBTTagCompound::setByte))
			.register(byte.class, NBTTagCompound::getByte, NBTTagCompound::setByte)
			.register(byte[].class, wrap(NBTTagCompound::getByteArray), wrap(NBTTagCompound::setByteArray))
			.register(int[].class, wrap(NBTTagCompound::getIntArray), wrap(NBTTagCompound::setIntArray))
			.register(UUID.class, wrap(NBTTagCompound::getUniqueId), wrap(NBTTagCompound::setUniqueId))
			.register(NBTTagCompound.class, wrap(NBTTagCompound::getCompoundTag), wrap(NBTTagCompound::setTag))
			.register(NBTBase.class, wrap(NBTTagCompound::getTag), wrap(NBTTagCompound::setTag))
			.finish();

	private static <T> NBTWriter<T> wrap(final NBTWriter<T> writer)
	{
		return (nbt, id, v)->{
			if(v != null)
				writer.writeToNBT(nbt, id, v);
		};
	}

	private static <T> NBTReader<T> wrap(final NBTReader<T> reader)
	{
		return (nbt, id)->{
			if(nbt.hasKey(id))
				return reader.readFromNBT(nbt, id);
			return null;
		};
	}
}

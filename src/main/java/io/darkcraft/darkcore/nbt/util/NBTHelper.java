package io.darkcraft.darkcore.nbt.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.impl.wrappers.PrimitiveWrappers;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;
import io.darkcraft.darkcore.nbt.mapper.PartialMapper;

public class NBTHelper
{
	public static NBTHelper INSTANCE = PrimitiveWrappers.addWrappers(new NBTHelper());

	private final List<PartialMapper> partialMappers = new ArrayList<>();

	NBTHelper(){};

	public <T> void addPartialMapper(PartialMapper mapper)
	{
		partialMappers.add(mapper);
	}

	public NBTMapperBuilder buildMapper()
	{
		return new NBTMapperBuilder(partialMappers);
	}

	public static <T> Optional<T> read(@Nullable NBTTagCompound compound, String id, NBTReader<T> reader)
	{
		if(compound == null)
			return Optional.empty();
		else
			return Optional.ofNullable(reader.readFromNBT(compound, id));
	}

	public static <T> void write(@Nonnull NBTTagCompound compound, String id, NBTWriter<T> writer,
			@Nullable T val)
	{
		if(val != null)
			writer.writeToNBT(compound, id, val);
	}
}

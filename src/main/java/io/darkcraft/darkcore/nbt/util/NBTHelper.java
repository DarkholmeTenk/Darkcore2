package io.darkcraft.darkcore.nbt.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.impl.wrappers.PrimitiveWrappers;
import io.darkcraft.darkcore.nbt.impl.wrappers.array.ArrayMapperImpl;
import io.darkcraft.darkcore.nbt.impl.wrappers.collection.CollectionMapperImpl;
import io.darkcraft.darkcore.nbt.impl.wrappers.enums.EnumMapper;
import io.darkcraft.darkcore.nbt.impl.wrappers.map.MapMapperImpl;
import io.darkcraft.darkcore.nbt.impl.wrappers.minecraft.MinecraftWrapper;
import io.darkcraft.darkcore.nbt.mapper.NBTFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;
import io.darkcraft.darkcore.nbt.mapper.PartialMapper;

public class NBTHelper
{
	public static NBTHelper INSTANCE = new NBTHelper();

	private final List<PartialMapper> partialMappers = new ArrayList<>();

	NBTHelper()
	{
		partialMappers.add(PrimitiveWrappers.PARTIAL);
		partialMappers.add(MinecraftWrapper.PARTIAL);
		partialMappers.add(new EnumMapper());
		partialMappers.add(new ArrayMapperImpl());
		partialMappers.add(new CollectionMapperImpl());
		partialMappers.add(new MapMapperImpl());
	};

	public <T> void addPartialMapper(PartialMapper mapper)
	{
		partialMappers.add(mapper);
	}

	public NBTMapperBuilder buildDefaultMapper()
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

	/**
	 * Attempts to fill an existing value and returns whether or not it succeeded
	 * @return true if filling succeeded
	 */
	public static <T> boolean fill(@Nullable NBTFiller<T> filler, @Nonnull NBTTagCompound nbt, @Nonnull String id,
			@Nullable T existing)
	{
		if((filler == null) || (existing == null) || !filler.isValid(nbt, id, existing))
			return false;
		return true;
	}
}

package io.darkcraft.darkcore.nbt.impl.subtype;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.exception.NBTMapperBuildException;
import io.darkcraft.darkcore.nbt.mapper.NBTFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTFiller.NBTObjFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTReader.NBTObjReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter.NBTObjWriter;

public class SubTypeMapperContainer<T>
{
	private final NBTReader<T> reader;
	private final NBTWriter<T> writer;
	private final NBTFiller<T> filler;

	SubTypeMapperContainer(NBTReader<T> reader, NBTWriter<T> writer, NBTFiller<T> filler)
	{
		this.reader = reader;
		this.writer = writer;
		this.filler = filler;
		if((reader instanceof SubTypeMapperContainer)
				|| (writer instanceof SubTypeMapperContainer)
				|| (filler instanceof SubTypeMapperContainer))
			throw new NBTMapperBuildException("Circular reference found " + reader + "," + writer + "," + filler);
	}

	public T readFromNBT(NBTTagCompound nbt)
	{
		if(reader instanceof NBTObjReader)
			return ((NBTObjReader<T>)reader).readFromNBT(nbt);
		else
			return reader.readFromNBT(nbt, "val");
	}

	public void writeToNBT(NBTTagCompound nbt, T value)
	{
		if(writer instanceof NBTObjWriter)
			((NBTObjWriter<T>)writer).writeToNBT(nbt, value);
		else
			writer.writeToNBT(nbt, "val", value);
	}

	public void fillFromNBT(NBTTagCompound nbt, T existing)
	{
		if(filler instanceof NBTObjFiller)
			((NBTObjFiller<T>)filler).fillFromNBT(nbt, existing);
		else
			filler.fillFromNBT(nbt, "val", existing);
	}

	public boolean isValid(NBTTagCompound nbt, T existing)
	{
		return filler == null;
	}

	public static <T> SubTypeMapperContainer<T> construct(Class<T> clz, NBTMapper mapper)
	{
		NBTReader<T> reader = mapper.getReader(clz);
		NBTWriter<T> writer = mapper.getWriter(clz);
		NBTFiller<T> filler = mapper.getFiller(clz);
		return new SubTypeMapperContainer(reader, writer, filler);
	}
}

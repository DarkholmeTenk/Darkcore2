package io.darkcraft.darkcore.nbt.impl.reflection.reading;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.mapper.NBTFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;

class FillingField<T,V>
{
	private final ReadingField<T,V> reader;
	private final WritingField<T,V> writer;
	private final NBTFiller<V> filler;

	FillingField(ReadingField<T, V> reader, WritingField<T,V> writer, NBTFiller<V> filler)
	{
		this.reader = reader;
		this.writer = writer;
		this.filler = filler;
	}

	public void read(NBTTagCompound nbt, String id, T obj)
	{
		if((filler == null) || (writer == null))
			reader.read(nbt, id, obj);
		else
		{
			V v = writer.field.apply(obj);
			if(v == null)
				reader.read(nbt, id, obj);
			else
				filler.fillFromNBT(nbt, id, v);
		}
	}

	private static <T,V> FillingField<T,V> getField(NBTMapper parent,
			ReadingField<T,?> readerI, WritingField<T,?> writerI)
	{
		ReadingField<T,V> reader = (ReadingField<T, V>) readerI;
		if(writerI != null)
		{
			WritingField<T,V> writer = (WritingField<T, V>) writerI;
			Type type = reader.type;
			NBTFiller<V> filler = parent.getFiller(type);
			if(filler != null)
				return new FillingField<>(reader, writer, filler);
		}
		return new FillingField<>(reader, null, null);
	}

	public static <T> Map<String, FillingField<T,?>> construct(NBTMapper parent, Class<T> baseClass)
	{
		Map<String, WritingField<T,?>> writers = ReflectionWriter.getFields(parent, baseClass);
		Map<String, ReadingField<T,?>> readers = ReflectionReader.getFields(parent, new HashSet<>(), baseClass);
		Map<String, FillingField<T,?>> fillers = new HashMap<>();
		for(Entry<String, ReadingField<T, ?>> reader : readers.entrySet())
			fillers.put(reader.getKey(), getField(parent, reader.getValue(), writers.get(reader.getKey())));
		return fillers;
	}
}

package io.darkcraft.darkcore.nbt.impl.wrappers.map;

import static io.darkcraft.darkcore.nbt.impl.wrappers.map.MapConstants.SIZE_KEY;
import static io.darkcraft.darkcore.nbt.impl.wrappers.map.MapConstants.getMapKeyKey;
import static io.darkcraft.darkcore.nbt.impl.wrappers.map.MapConstants.getMapValKey;
import static io.darkcraft.darkcore.nbt.util.NBTHelper.write;

import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.mapper.NBTWriter;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter.NBTObjWriter;

public class MapWriter<K,V> implements NBTObjWriter<Map<K,V>>
{
	private final NBTWriter<K> keyWriter;
	private final NBTWriter<V> valWriter;

	public MapWriter(NBTWriter<K> keyWriter,
			NBTWriter<V> valWriter)
	{
		this.keyWriter = keyWriter;
		this.valWriter = valWriter;
	}

	@Override
	public void writeToNBT(NBTTagCompound child, Map<K, V> value)
	{
		child.setInteger(SIZE_KEY, value.size());
		int i = 0;
		for(Entry<K,V> entry : value.entrySet())
		{
			write(child, getMapKeyKey(i), keyWriter, entry.getKey());
			write(child, getMapValKey(i), valWriter, entry.getValue());
			i++;
		}
	}
}

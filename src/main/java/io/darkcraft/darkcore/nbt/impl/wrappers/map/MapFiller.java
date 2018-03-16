package io.darkcraft.darkcore.nbt.impl.wrappers.map;

import static io.darkcraft.darkcore.nbt.impl.wrappers.map.MapConstants.getMapKeyKey;
import static io.darkcraft.darkcore.nbt.impl.wrappers.map.MapConstants.getMapValKey;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.mapper.NBTFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTFiller.NBTObjFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.util.NBTHelper;

public class MapFiller<K,V> implements NBTObjFiller<Map<K,V>>
{
	private final NBTReader<K> keyReader;
	private final NBTReader<V> valReader;
	private final NBTFiller<V> valFiller;

	MapFiller(NBTReader<K> keyReader, NBTReader<V> valReader, NBTFiller<V> valFiller)
	{
		this.keyReader = keyReader;
		this.valReader = valReader;
		this.valFiller = valFiller;
	}

	@Override
	public void fillFromNBT(NBTTagCompound nbt, Map<K, V> existing)
	{
		int s = nbt.getInteger(MapConstants.SIZE_KEY);
		Set<K> newKeys = new HashSet<>();
		for(int i = 0; i < s; i++)
		{
			K key = keyReader.readFromNBT(nbt, getMapKeyKey(i));
			newKeys.add(key);
			if(!NBTHelper.fill(valFiller, nbt, getMapValKey(i), existing.get(key)))
				existing.put(key, valReader.readFromNBT(nbt, getMapValKey(i)));
		}
		Iterator<K> keyIter = existing.keySet().iterator();
		while(keyIter.hasNext())
			if(!newKeys.contains(keyIter.next()))
				keyIter.remove();
	}

	@Override
	public boolean isValid(NBTTagCompound nbt, Map<K, V> existing)
	{
		return true;
	}

}

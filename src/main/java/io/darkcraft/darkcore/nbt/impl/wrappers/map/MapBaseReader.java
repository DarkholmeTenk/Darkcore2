package io.darkcraft.darkcore.nbt.impl.wrappers.map;

import static io.darkcraft.darkcore.nbt.impl.wrappers.map.MapConstants.SIZE_KEY;
import static io.darkcraft.darkcore.nbt.impl.wrappers.map.MapConstants.getMapKeyKey;
import static io.darkcraft.darkcore.nbt.impl.wrappers.map.MapConstants.getMapValKey;
import static io.darkcraft.darkcore.nbt.util.NBTHelper.read;

import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.mapper.NBTReader;

public class MapBaseReader<K,V, U extends Map<K,V>> implements NBTReader<U>
{
	private final NBTReader<K> keyReader;
	private final NBTReader<V> valReader;
	private final Supplier<U> mapMaker;

	public MapBaseReader(NBTReader<K> keyReader,
				NBTReader<V> valReader,
				Supplier<U> mapMaker)
	{
		this.keyReader = keyReader;
		this.valReader = valReader;
		this.mapMaker = mapMaker;
	}

	@Override
	public U readFromNBT(NBTTagCompound nbt, String id)
	{
		NBTTagCompound child = nbt.getCompoundTag(id);
		U map = mapMaker.get();
		int size = child.getInteger(SIZE_KEY);
		for(int i = 0; i < size; i++)
		{
			K k = read(child, getMapKeyKey(i), keyReader).orElse(null);
			V v = read(child, getMapValKey(i), valReader).orElse(null);
			map.put(k, v);
		}
		return map;
	}
}
package io.darkcraft.darkcore.nbt.impl.wrappers.map;

final class MapConstants
{
	private MapConstants() {}
	static final String SIZE_KEY = "S";
	static final String MAP_KEY_PREFIX = "K";
	static final String MAP_VAL_PREFIX = "V";

	static String getMapKeyKey(int i)
	{
		return MAP_KEY_PREFIX + i;
	}

	static String getMapValKey(int i)
	{
		return MAP_VAL_PREFIX + i;
	}
}

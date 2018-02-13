package io.darkcraft.darkcore.nbt.impl.wrappers.collection;

final class CollectionConstants
{
	private CollectionConstants() {}
	static final String SIZE_KEY = "S";
	static final String MAP_KEY_PREFIX = "K";
	static final String MAP_VAL_PREFIX = "V";

	static String getKey(int i)
	{
		return String.valueOf(i);
	}

	static String getMapKeyKey(int i)
	{
		return MAP_KEY_PREFIX + i;
	}

	static String getMapValKey(int i)
	{
		return MAP_VAL_PREFIX + i;
	}
}

package io.darkcraft.darkcore.nbt.impl.wrappers.collection;

final class CollectionConstants
{
	private CollectionConstants() {}
	static final String SIZE_KEY = "S";

	static String getKey(int i)
	{
		return String.valueOf(i);
	}
}

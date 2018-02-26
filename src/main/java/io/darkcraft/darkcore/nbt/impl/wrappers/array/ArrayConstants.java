package io.darkcraft.darkcore.nbt.impl.wrappers.array;

final class ArrayConstants
{
	private ArrayConstants() {}
	static final String SIZE_KEY = "S";

	static String getKey(int i)
	{
		return String.valueOf(i);
	}
}

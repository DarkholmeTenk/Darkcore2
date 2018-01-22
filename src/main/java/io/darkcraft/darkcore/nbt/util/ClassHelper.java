package io.darkcraft.darkcore.nbt.util;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public final class ClassHelper
{
	private static Map<Class<?>, Class<?>> BOX_MAP = ImmutableMap.<Class<?>,Class<?>>builder()
			.put(Boolean.TYPE, Boolean.class)
			.put(Character.TYPE, Character.class)
			.put(Byte.TYPE, Byte.class)
			.put(Short.TYPE, Short.class)
			.put(Integer.TYPE, Integer.class)
			.put(Long.TYPE, Long.class)
			.put(Float.TYPE, Float.class)
			.put(Double.TYPE, Double.class)
			.build();

	public static Class<?> wrapPrimitive(Class<?> clazz)
	{
		if(!clazz.isPrimitive())
			return clazz;
		return BOX_MAP.getOrDefault(clazz, clazz);
	}

	private ClassHelper()
	{
		//This is a static helper class and should not be instantiable
	}
}

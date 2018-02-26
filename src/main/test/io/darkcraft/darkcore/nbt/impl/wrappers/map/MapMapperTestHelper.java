package io.darkcraft.darkcore.nbt.impl.wrappers.map;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

class MapMapperTestHelper
{
	public Map<String, String> mapStrStr = new HashMap<>(ImmutableMap.of("A", "A", "B", "B", "C", "C"));

	public Map<String, List<String>> mapStrListStr = new HashMap<>(
			ImmutableMap.of(
					"A", Arrays.asList("A","A","A"),
					"B", Arrays.asList("B","B","B"),
					"C", Arrays.asList("CD","DE", "EF")));

	private static Type getType(String field)
	{
		try
		{
			return MapMapperTestHelper.class.getField(field).getGenericType();
		}
		catch(Exception e)
		{
			throw new RuntimeException("Error getting " + field, e);
		}
	}

	public static Type mapStrStr()
	{
		return getType("mapStrStr");
	}

	public static Type mapStrListStr()
	{
		return getType("mapStrListStr");
	}
}

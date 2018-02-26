package io.darkcraft.darkcore.nbt.impl.wrappers.array;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class ArrayMapperTestHelper
{
	public String[][][] strStrStrArr = new String[][][] {{{"A"},{"B"}},{{"C","D"}}};

	public int[] intArr = new int[] {1,2,3,4};
	public int[][] intArrArr = new int[][] {{1,2},{3,4}};
	public List<String>[] listStrArr = new List[]{Arrays.asList("a","b","c"),Arrays.asList("d","e","f")};

	private static Type getType(String field)
	{
		try
		{
			return ArrayMapperTestHelper.class.getField(field).getGenericType();
		}
		catch(Exception e)
		{
			throw new RuntimeException("Error getting " + field, e);
		}
	}

	public static Type strStrStrArr()
	{
		return getType("strStrStrArr");
	}

	public static Type intArr()
	{
		return getType("intArr");
	}

	public static Type intArrArr()
	{
		return getType("intArrArr");
	}

	public static Type listStrArr()
	{
		return getType("listStrArr");
	}
}

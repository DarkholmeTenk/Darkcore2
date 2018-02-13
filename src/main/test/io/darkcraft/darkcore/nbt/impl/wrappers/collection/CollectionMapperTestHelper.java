package io.darkcraft.darkcore.nbt.impl.wrappers.collection;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CollectionMapperTestHelper
{
	public List<String> stringList;

	public List<Integer> intList;

	public ArrayList<Integer> intArrList;

	public LinkedList<Integer> intLinkList;

	public Set<String> stringSet;

	public HashSet<String> stringHashSet;

	public static Type getStrList() throws NoSuchFieldException, SecurityException
	{
		return CollectionMapperTestHelper.class.getField("stringList").getGenericType();
	}

	public static Type getIntList() throws NoSuchFieldException, SecurityException
	{
		return CollectionMapperTestHelper.class.getField("intList").getGenericType();
	}

	public static Type getIntArrList() throws NoSuchFieldException, SecurityException
	{
		return CollectionMapperTestHelper.class.getField("intArrList").getGenericType();
	}

	public static Type getIntLinkList() throws NoSuchFieldException, SecurityException
	{
		return CollectionMapperTestHelper.class.getField("intLinkList").getGenericType();
	}

	public static Type getStrSet() throws NoSuchFieldException, SecurityException
	{
		return CollectionMapperTestHelper.class.getField("stringSet").getGenericType();
	}

	public static Type getStrHashSet() throws NoSuchFieldException, SecurityException
	{
		return CollectionMapperTestHelper.class.getField("stringHashSet").getGenericType();
	}
}

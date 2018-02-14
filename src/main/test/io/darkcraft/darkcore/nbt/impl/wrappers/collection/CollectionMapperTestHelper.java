package io.darkcraft.darkcore.nbt.impl.wrappers.collection;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CollectionMapperTestHelper
{
	public List<String> stringList = Arrays.asList("A","B","C");

	public List<Integer> intList = Arrays.asList(1, 2, 3);

	public ArrayList<Integer> intArrList = new ArrayList<>(intList);

	public LinkedList<Integer> intLinkList = new LinkedList<>(intList);

	public Set<String> stringSet = new HashSet<>(stringList);

	public HashSet<String> stringHashSet = new HashSet<>(stringList);

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

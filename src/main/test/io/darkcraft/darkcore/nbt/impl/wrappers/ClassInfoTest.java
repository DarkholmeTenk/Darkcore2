package io.darkcraft.darkcore.nbt.impl.wrappers;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

import org.junit.Test;

public class ClassInfoTest
{

	public static class T
	{
		public Map<String, Long> map;
	}

	@Test
	public void test() throws NoSuchFieldException, SecurityException
	{
		Field f = T.class.getField("map");
		Class<?> c = f.getType();
		AnnotatedType at = f.getAnnotatedType();
		Type t = f.getAnnotatedType().getType();
		System.out.println();
	}
}

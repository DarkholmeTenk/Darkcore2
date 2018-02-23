package io.darkcraft.darkcore.nbt.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeKey<T>
{
	public Type getType()
	{
		Class clz = this.getClass();
		Type sc = clz.getGenericSuperclass();
		return ((ParameterizedType) sc).getActualTypeArguments()[0];
	}
}

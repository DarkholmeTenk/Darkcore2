package io.darkcraft.darkcore.nbt.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.darkcraft.darkcore.nbt.annot.NBTView;

public final class ReflectHelper
{
	private static final Logger LOGGER = LogManager.getLogger(ReflectHelper.class);

	private ReflectHelper() {}

	public static Class<?> getBaseClass(Type type)
	{
		if(type instanceof Class)
			return (Class<?>) type;
		if(type instanceof AnnotatedType)
			return getBaseClass(((AnnotatedType)type).getType());
		if(type instanceof ParameterizedType)
			return getBaseClass(((ParameterizedType)type).getRawType());
		if(type instanceof GenericArrayType)
			return Array.newInstance(getBaseClass(((GenericArrayType)type).getGenericComponentType()), 0).getClass();
		LOGGER.warn("Unable to work out base class for " + type);
		return null;
	}

	public static boolean isValid(NBTView view, Class<?> viewClass)
	{
		if((view == null) || (viewClass == null))
			return true;
		for(Class<?> clz : view.value())
			if(viewClass.isAssignableFrom(clz) || clz.isAssignableFrom(viewClass))
				return true;
		return false;
	}

	public static String resolveMethodName(String name)
	{
		if((name.startsWith("get") || name.startsWith("set")) && (name.length() > 4))
			return name.substring(3, 4).toLowerCase() + name.substring(4);
		if(name.startsWith("is") && (name.length() > 3))
			return name.substring(2, 3).toLowerCase() + name.substring(3);
		return name;
	}

	public static <T extends Annotation> Optional<T> getAnnotation(AnnotatedElement f, Class<T> clazz)
	{
		return Optional.ofNullable(f.getAnnotation(clazz));
	}

	public static <T extends Annotation> Optional<T> getAnnotation(Method m, Class<T> clazz)
	{
		return Optional.ofNullable(m.getAnnotation(clazz));
	}
}

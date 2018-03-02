package io.darkcraft.darkcore.nbt.impl.reflection.reading;

import static io.darkcraft.darkcore.nbt.util.ReflectHelper.getAnnotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.annot.NBTProperty;
import io.darkcraft.darkcore.nbt.annot.NBTView;
import io.darkcraft.darkcore.nbt.exception.NBTMapperBuildException;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader.NBTObjReader;
import io.darkcraft.darkcore.nbt.util.ReflectHelper;

public class ReflectionReader<T> implements NBTObjReader<T>
{
	private final ReadingConstructor<T> constructor;
	private final Map<String, ReadingField<T,?>> readers;

	private ReflectionReader(ReadingConstructor<T> constructor, Map<String, ReadingField<T,?>> readers)
	{
		this.constructor = constructor;
		this.readers = readers;
	}

	@Override
	public T readFromNBT(NBTTagCompound nbt)
	{
		T t = constructor.readFromNBT(nbt);
		readers.forEach((s,f)->f.read(nbt, s, t));
		return t;
	}

	private static <T> void getReadingFields(Map<String, ReadingField<T,?>> map,
			NBTMapper parent, Set<String> alreadyHandled, Class<?> baseClass, Class<?> viewClass)
	{
		if(baseClass.getSuperclass() != null)
			getReadingFields(map, parent, alreadyHandled, baseClass.getSuperclass(), viewClass);
		for(Field f : baseClass.getDeclaredFields())
		{
			Optional<NBTProperty> prop = getAnnotation(f, NBTProperty.class);
			NBTView view = f.getAnnotation(NBTView.class);
			if(!prop.isPresent() || !ReflectHelper.isValid(view, viewClass))
				continue;
			int modifiers = f.getModifiers();
			if(Modifier.isFinal(modifiers))
				continue;
			f.setAccessible(true);
			String name = prop.map(NBTProperty::value)
					.filter(n->!n.isEmpty())
					.orElseGet(()->f.getName());
			if(!alreadyHandled.contains(name))
				map.put(name, ReadingField.getField(parent, f));
		}
		for(Method m : baseClass.getDeclaredMethods())
		{
			Optional<NBTProperty> prop = getAnnotation(m, NBTProperty.class);
			NBTView view = m.getAnnotation(NBTView.class);
			if(!prop.isPresent() || !ReflectHelper.isValid(view, viewClass) || (m.getParameterCount() == 0))
				continue;
			if(m.getParameterCount() > 1)
				throw new NBTMapperBuildException("Unable to use method " + m + " as a setter as it doesn't take 1 param");
			m.setAccessible(true);
			String name = prop.map(NBTProperty::value)
					.filter(n->!n.isEmpty())
					.orElseGet(()->ReflectHelper.resolveMethodName(m.getName()));
			if(!alreadyHandled.contains(name))
				map.put(name, ReadingField.getField(parent, m));
		}
	}

	static <T> Map<String, ReadingField<T,?>> getFields(NBTMapper parent, Set<String> alreadyHandled,
			Class<T> baseClass)
	{
		Map<String, ReadingField<T, ?>> map = new HashMap<>();
		getReadingFields(map, parent, alreadyHandled, baseClass, parent.getViewClass());
		return map;
	}

	public static <T> ReflectionReader<T> construct(NBTMapper parent, Class<T> baseClass)
	{
		ReadingConstructor<T> constructor = ReadingConstructor.construct(parent, baseClass);
		Map<String, ReadingField<T,?>> fields = getFields(parent, constructor.getFields(), baseClass);
		return new ReflectionReader(constructor, fields);
	}
}

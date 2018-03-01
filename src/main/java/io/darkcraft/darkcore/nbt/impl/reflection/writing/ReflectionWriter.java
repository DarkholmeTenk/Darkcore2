package io.darkcraft.darkcore.nbt.impl.reflection.writing;

import static io.darkcraft.darkcore.nbt.util.ReflectHelper.getAnnotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.annot.NBTProperty;
import io.darkcraft.darkcore.nbt.annot.NBTView;
import io.darkcraft.darkcore.nbt.exception.NBTMapperBuildException;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter.NBTObjWriter;
import io.darkcraft.darkcore.nbt.util.ReflectHelper;

public class ReflectionWriter<T> implements NBTObjWriter<T>
{
	private static final Logger LOGGER = LogManager.getLogger(ReflectionWriter.class);

	private final Map<String, WritingField<T,?>> writers;

	private ReflectionWriter(Map<String, WritingField<T,?>> writers)
	{
		this.writers = writers;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, T value)
	{
		for(Entry<String, WritingField<T, ?>> entry : writers.entrySet())
			entry.getValue().write(nbt, entry.getKey(), value);
	}

	public Set<String> getNames()
	{
		return new HashSet<>(writers.keySet());
	}

	private static <T> void fillMap(Map<String, WritingField<T,?>> map,
			NBTMapper parent, Class<?> viewClass, Class<? super T> baseClass)
	{
		for(Field f : baseClass.getDeclaredFields())
		{
			Optional<NBTProperty> prop = getAnnotation(f, NBTProperty.class);
			NBTView view = f.getAnnotation(NBTView.class);
			if(!prop.isPresent() || !ReflectHelper.isValid(view, viewClass))
				continue;
			f.setAccessible(true);
			String name = prop.map(NBTProperty::value)
					.filter(n->!n.isEmpty())
					.orElseGet(()->f.getName());
			map.put(name, WritingField.getField(parent, f));
		}
		for(Method m : baseClass.getDeclaredMethods())
		{
			Optional<NBTProperty> prop = getAnnotation(m, NBTProperty.class);
			NBTView view = m.getAnnotation(NBTView.class);
			if(!prop.isPresent() || !ReflectHelper.isValid(view, viewClass))
				continue;
			if((m.getReturnType() == null) || (m.getReturnType() == Void.TYPE))
				continue;
			if(m.getParameterCount() > 0)
				throw new NBTMapperBuildException("Unable to use method " + m + " as a getter as it takes params");
			m.setAccessible(true);
			String name = prop.map(NBTProperty::value)
					.filter(n->!n.isEmpty())
					.orElseGet(()->ReflectHelper.resolveMethodName(m.getName()));
			map.put(name, WritingField.getField(parent, m));
		}
		if(baseClass.getSuperclass() != null)
			fillMap(map, parent, viewClass, baseClass.getSuperclass());
	}

	public static <T> ReflectionWriter<T> construct(NBTMapper parent, Class<T> baseClass)
	{
		try
		{
			Map<String, WritingField<T,?>> map = new HashMap<>();
			Class<?> viewClass = parent.getViewClass();
			fillMap(map, parent, viewClass, baseClass);
			return new ReflectionWriter<>(map);
		}
		catch(SecurityException e)
		{
			LOGGER.error("Error construcing mapper", e);
			throw new NBTMapperBuildException("Unable to construct mapper for " + baseClass, e);
		}
	}
}
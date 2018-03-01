package io.darkcraft.darkcore.nbt.annot;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * An interface to indicate to the ReflectionMapper that this class should have a reflection mapper created for
 * it.
 * @author dark
 *
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface NBTReflect
{
	/**
	 * @return true if a filler should be generated from this type
	 */
	boolean canFill() default true;
}

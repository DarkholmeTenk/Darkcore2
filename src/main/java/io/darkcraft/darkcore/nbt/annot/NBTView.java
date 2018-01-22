package io.darkcraft.darkcore.nbt.annot;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Similar to {@link NBTProperty} this annotation marks an object for serialization/deserialization.<p/>
 *
 * However, if a writer is using a view class, then only properties annotated with the same class (or a superclass)
 * will be serialized/deserialized
 *
 * @author dark
 *
 */
@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface NBTView
{
	/**
	 * @return a list of classes which can be used to serialize this value
	 */
	Class<?>[] value() default {};

	/**
	 * @return the key which the value should be saved under, which will be calculated automatically if empty
	 */
	String key() default "";
}

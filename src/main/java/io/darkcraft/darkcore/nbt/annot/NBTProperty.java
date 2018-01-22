package io.darkcraft.darkcore.nbt.annot;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation has two uses.<p/>
 * <ul>
 * <li>Annotating methods and fields which should be serialized and deserialized in all cases</li>
 * <li>Annotating parameters on a constructor which is annotated with {@link NBTConstructor} to be
 * used to map fields to parameters</li>
 * </ul><p/>
 * @author dark
 *
 */
@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD, PARAMETER })
public @interface NBTProperty
{
	/**
	 * For serialization/deserialization, this value is the name of the nbt tag to save under.<p/>
	 * For {@link NBTConstructor} it serves as a way to indicate which field to map to
	 * @return a string to use as a key, or empty string to calculate automatically.
	 */
	String value() default "";
}

package io.darkcraft.darkcore.nbt.annot;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Can be annotated on a class to indicate that whenever a field of this type is serialized, it should be
 * wrapped in a Polymorphic wrapper as the class has subtypes which may need to be taken in to account.
 *
 * @author dark
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface NBTSubTypes
{
	/**
	 * @return a name that will be used in the nbt to save the type as
	 */
	String fieldName() default "typ";

	/**
	 * @return an id that will be used to serialise the value to if the mapper for a type is not
	 * an obj mapper
	 */
	String valName() default "o";

	/**
	 * @return an array of all the possible sub types that the class could end up being
	 */
	SubType[] value();

	/**
	 * @return a class which will be defaulted to if no class info is found
	 */
	Class<?> defaultClass() default Object.class;

	public @interface SubType
	{
		/**
		 * @return the class this subtype represents
		 */
		Class<?> clazz();
		/**
		 * @return the name the subtype will be stored as
		 */
		String name();
	}
}

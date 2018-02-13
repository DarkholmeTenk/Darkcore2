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

}

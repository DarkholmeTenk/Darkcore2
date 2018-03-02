package io.darkcraft.darkcore.nbt.annot;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * An annotation which can be placed on a constructor (or static method) which provides a way to reproduce
 * a new instance of the class.<p/>
 *
 * Parameters on the method can be annotated with {@link NBTProperty} to indicate that these values should be
 * passed in from specific fields.<p/>
 *
 * If the parameters are <em>not</em> annotated, the compiler option <code>-parameters</code> must be turned on,
 * otherwise they will come out as arg0, arg1, etc...
 * @author dark
 *
 */
@Documented
@Retention(RUNTIME)
@Target({ METHOD, CONSTRUCTOR })
public @interface NBTConstructor
{

}

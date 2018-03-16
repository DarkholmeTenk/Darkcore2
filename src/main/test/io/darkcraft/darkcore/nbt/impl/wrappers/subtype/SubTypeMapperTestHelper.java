package io.darkcraft.darkcore.nbt.impl.wrappers.subtype;

import io.darkcraft.darkcore.nbt.annot.NBTProperty;
import io.darkcraft.darkcore.nbt.annot.NBTReflect;
import io.darkcraft.darkcore.nbt.annot.NBTSubTypes;
import io.darkcraft.darkcore.nbt.annot.NBTSubTypes.SubType;

public class SubTypeMapperTestHelper
{
	@NBTReflect
	@NBTSubTypes(value={
		@SubType(name="A", clazz=A.class),
		@SubType(name="B", clazz=B.class)
	},
	defaultClass=C.class)
	public static class Base{}

	@NBTReflect
	public static class A extends Base{}
	@NBTReflect
	public static class B extends Base{}
	@NBTReflect
	public static class C extends Base{}

	@NBTReflect
	public static class Container
	{
		@NBTProperty
		public Base obj;
	}
}

package io.darkcraft.darkcore.nbt.impl.wrappers.reflection;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import io.darkcraft.darkcore.nbt.annot.NBTProperty;
import io.darkcraft.darkcore.nbt.annot.NBTReflect;

@NBTReflect
public class ReflectionTestHelper
{
	@NBTProperty
	private String a;

	@NBTProperty
	private int b;

	@NBTProperty("c")
	private List<UUID> uuidList;

	@NBTProperty
	@NBTView()
}

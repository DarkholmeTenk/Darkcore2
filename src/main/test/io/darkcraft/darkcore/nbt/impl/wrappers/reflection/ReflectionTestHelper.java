package io.darkcraft.darkcore.nbt.impl.wrappers.reflection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import io.darkcraft.darkcore.nbt.annot.NBTConstructor;
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

	private Map<Long, String> map;

	private final String name;

	@NBTProperty
	private final int age;

	@NBTProperty("field")
	private final int lolwut;

	@NBTConstructor
	public ReflectionTestHelper(
			@NBTProperty("name") String name,
			@NBTProperty("age") int age,
			@NBTProperty("field") int lolwut)
	{
		this.name = name;
		this.age = age;
		this.lolwut = lolwut;
	}

	@NBTProperty
	public String getName()
	{
		return name;
	}

	@NBTProperty
	public Map<Long, String> getMap()
	{
		return map;
	}

	@NBTProperty
	public void setMap(Map<Long, String> map)
	{
		if(map != null)
			this.map = new HashMap<>(map);
	}

	public void setA(String a)
	{
		this.a = a;
	}

	public void setB(int b)
	{
		this.b = b;
	}

	public void setUuidList(List<UUID> uuidList)
	{
		this.uuidList = uuidList;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(a,b,uuidList, map, name, age, lolwut);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (!(obj instanceof ReflectionTestHelper)) return false;
		ReflectionTestHelper other = (ReflectionTestHelper) obj;
		if(!Objects.equals(a, other.a)) return false;
		if (age != other.age) return false;
		if (b != other.b) return false;
		if (lolwut != other.lolwut) return false;
		if(!Objects.equals(map, other.map)) return false;
		if(!Objects.equals(name, other.name)) return false;
		if(!Objects.equals(uuidList, other.uuidList)) return false;
		return true;
	}
}

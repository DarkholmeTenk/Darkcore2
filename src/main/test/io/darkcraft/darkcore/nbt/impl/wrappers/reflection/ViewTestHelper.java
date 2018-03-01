package io.darkcraft.darkcore.nbt.impl.wrappers.reflection;

import java.util.Objects;

import io.darkcraft.darkcore.nbt.annot.NBTProperty;
import io.darkcraft.darkcore.nbt.annot.NBTReflect;
import io.darkcraft.darkcore.nbt.annot.NBTView;

@NBTReflect
public class ViewTestHelper
{
	public static interface A{}

	public static interface B{}

	public static interface AA extends A{}

	public static interface AB extends A{}

	public static interface AAA extends AA{}

	public static interface AAB extends AA{}

	public static interface BOTH extends A, B{}

	public static interface C{}

	@NBTProperty
	@NBTView(A.class)
	public int a = 0;

	@NBTProperty
	@NBTView(B.class)
	public int b = 0;

	@NBTProperty
	@NBTView(AA.class)
	public int aa = 0;

	@NBTProperty
	@NBTView(AB.class)
	public int ab = 0;

	@NBTProperty
	@NBTView(AAA.class)
	public int aaa = 0;

	@NBTProperty
	@NBTView(AAB.class)
	public int aab = 0;

	@NBTProperty
	@NBTView(BOTH.class)
	public int both = 0;

	@NBTProperty
	@NBTView({A.class, B.class})
	public int both2 = 0;

	@NBTProperty
	public int none = 0;

	@Override
	public int hashCode()
	{
		return Objects.hash(a,b,aa,ab,aaa,aab,both,both2,none);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (!(obj instanceof ViewTestHelper)) return false;
		ViewTestHelper other = (ViewTestHelper) obj;
		if (a != other.a) return false;
		if (aa != other.aa) return false;
		if (aaa != other.aaa) return false;
		if (aab != other.aab) return false;
		if (ab != other.ab) return false;
		if (b != other.b) return false;
		if (both != other.both) return false;
		if (both2 != other.both2) return false;
		if (none != other.none) return false;
		return true;
	}

	public void set(int newVal)
	{
		a = newVal;
		b = newVal;
		aa = newVal;
		ab = newVal;
		aaa = newVal;
		aab = newVal;
		both = newVal;
		both2 = newVal;
		none = newVal;
	}
}

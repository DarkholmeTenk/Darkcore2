package io.darkcraft.darkcore.nbt.impl.wrappers.reflection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.impl.wrappers.reflection.ViewTestHelper.A;
import io.darkcraft.darkcore.nbt.impl.wrappers.reflection.ViewTestHelper.AA;
import io.darkcraft.darkcore.nbt.impl.wrappers.reflection.ViewTestHelper.AAA;
import io.darkcraft.darkcore.nbt.impl.wrappers.reflection.ViewTestHelper.AAB;
import io.darkcraft.darkcore.nbt.impl.wrappers.reflection.ViewTestHelper.AB;
import io.darkcraft.darkcore.nbt.impl.wrappers.reflection.ViewTestHelper.B;
import io.darkcraft.darkcore.nbt.impl.wrappers.reflection.ViewTestHelper.C;
import io.darkcraft.darkcore.nbt.mapper.NBTFiller.NBTObjFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader.NBTObjReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter.NBTObjWriter;
import io.darkcraft.darkcore.nbt.util.NBTHelper;

@RunWith(Parameterized.class)
public class ViewTest
{
	NBTObjReader<ViewTestHelper> reader;
	NBTObjWriter<ViewTestHelper> writer;
	NBTObjFiller<ViewTestHelper> filler;
	private ViewTestHelper vth;
	private ViewTestHelper vth1;

	@Before
	public void setup()
	{
		vth = new ViewTestHelper();
		vth1 = new ViewTestHelper();
		vth1.set(1);
		if(viewClass == null)
			throw new RuntimeException("AAA");
		NBTMapper mapper = NBTHelper.INSTANCE.buildDefaultMapper()
				.withView(viewClass)
				.build();
		reader = (NBTObjReader<ViewTestHelper>) mapper.getReader(ViewTestHelper.class);
		writer = (NBTObjWriter<ViewTestHelper>) mapper.getWriter(ViewTestHelper.class);
		filler = (NBTObjFiller<ViewTestHelper>) mapper.getFiller(ViewTestHelper.class);
	}

	@Parameters(name= "{0}")
	public static Collection<Object[]> params()
	{
		List<Object[]> vals = new ArrayList<>();
		{
			ViewTestHelper vth = new ViewTestHelper();
			vth.set(1);
			vth.b = 0;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("a", 1);
			nbt.setInteger("aa", 1);
			nbt.setInteger("ab", 1);
			nbt.setInteger("aaa", 1);
			nbt.setInteger("aab", 1);
			nbt.setInteger("both", 1);
			nbt.setInteger("both2", 1);
			nbt.setInteger("none", 1);
			vals.add(new Object[] {A.class, vth, nbt});
		}
		{
			ViewTestHelper vth = new ViewTestHelper();
			vth.b = 1;
			vth.both = 1;
			vth.both2 = 1;
			vth.none = 1;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("b", 1);
			nbt.setInteger("both", 1);
			nbt.setInteger("both2", 1);
			nbt.setInteger("none", 1);
			vals.add(new Object[] {B.class, vth, nbt});
		}
		{
			ViewTestHelper vth = new ViewTestHelper();
			vth.a = 1;
			vth.aa = 1;
			vth.aaa = 1;
			vth.aab = 1;
			vth.both2 = 1;
			vth.none = 1;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("a", 1);
			nbt.setInteger("aa", 1);
			nbt.setInteger("aaa", 1);
			nbt.setInteger("aab", 1);
			nbt.setInteger("both2", 1);
			nbt.setInteger("none", 1);
			vals.add(new Object[] {AA.class, vth, nbt});
		}
		{
			ViewTestHelper vth = new ViewTestHelper();
			vth.a = 1;
			vth.ab = 1;
			vth.both2 = 1;
			vth.none = 1;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("a", 1);
			nbt.setInteger("ab", 1);
			nbt.setInteger("both2", 1);
			nbt.setInteger("none", 1);
			vals.add(new Object[] {AB.class, vth, nbt});
		}
		{
			ViewTestHelper vth = new ViewTestHelper();
			vth.a = 1;
			vth.aa = 1;
			vth.aaa = 1;
			vth.both2 = 1;
			vth.none = 1;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("a", 1);
			nbt.setInteger("aa", 1);
			nbt.setInteger("aaa", 1);
			nbt.setInteger("both2", 1);
			nbt.setInteger("none", 1);
			vals.add(new Object[] {AAA.class, vth, nbt});
		}
		{
			ViewTestHelper vth = new ViewTestHelper();
			vth.a = 1;
			vth.aa = 1;
			vth.aab = 1;
			vth.both2 = 1;
			vth.none = 1;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("a", 1);
			nbt.setInteger("aa", 1);
			nbt.setInteger("aab", 1);
			nbt.setInteger("both2", 1);
			nbt.setInteger("none", 1);
			vals.add(new Object[] {AAB.class, vth, nbt});
		}
		{
			ViewTestHelper vth = new ViewTestHelper();
			vth.none = 1;
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("none", 1);
			vals.add(new Object[] {C.class, vth, nbt});
		}
		return vals;
	}

	@Parameter(0)
	public Class<?> viewClass;

	@Parameter(1)
	public ViewTestHelper expectedObj;

	@Parameter(2)
	public NBTTagCompound expectedData;

	@Test
	public void testWrite()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writer.writeToNBT(nbt, vth1);
		assertEquals(expectedData, nbt);
	}

	@Test
	public void testRead()
	{
		ViewTestHelper newVTH = reader.readFromNBT(expectedData);
		assertEquals(expectedObj, newVTH);
	}

	@Test
	public void testFill()
	{
		assertNotEquals(expectedObj, vth);
		filler.fillFromNBT(expectedData, vth);
		assertEquals(expectedObj, vth);
	}
}

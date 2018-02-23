package io.darkcraft.darkcore.nbt.impl.wrappers.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.util.NBTHelper;

@RunWith(Parameterized.class)
public class MapMapperTest
{
	private static final MapMapperTestHelper helper = new MapMapperTestHelper();

	private NBTMapper mapper;
	private MapMapperImpl collMapper;
	private NBTTagCompound nbt;

	@Before
	public void before()
	{
		mapper = NBTHelper.INSTANCE.buildDefaultMapper().build();
		collMapper = new MapMapperImpl();
		nbt = new NBTTagCompound();
	}

	@Parameters(name="{0}")
	public static Collection<Object[]> getParameters()
	{
		return Arrays.asList(new Object[][] {
			{ MapMapperTestHelper.mapStrStr(), helper.mapStrStr, HashMap.class },
			{ MapMapperTestHelper.mapStrListStr(), helper.mapStrListStr, HashMap.class }
		});
	}

	@Parameter(0)
	public Type testType;

	@Parameter(1)
	public Map<?,?> value;

	@Parameter(2)
	public Class<?> expectedClass;

	@Test
	public void testWriterGet()
	{
		assertNotNull(collMapper.getWriter(mapper, testType));
	}

	@Test
	public void testReaderGet()
	{
		assertNotNull(collMapper.getReader(mapper, testType));
	}

	@Test
	public void testWriteAndRead()
	{
		collMapper.getWriter(mapper, testType).writeToNBT(nbt, "val", value);
		Map<?,?> ret = collMapper.<Map<?,?>>getReader(mapper, testType).readFromNBT(nbt, "val");
		assertEquals(value, ret);
	}

	@Test
	public void testExpectedClass()
	{
		collMapper.getWriter(mapper, testType).writeToNBT(nbt, "val", value);
		Map<?,?> ret = collMapper.<Map<?,?>>getReader(mapper, testType).readFromNBT(nbt, "val");
		assertEquals(expectedClass, ret.getClass());
	}

	@Test
	public void testMapFiller() throws InstantiationException, IllegalAccessException
	{
		Map<?,?> obj = (Map<?,?>) expectedClass.newInstance();
		collMapper.getWriter(mapper, testType).writeToNBT(nbt, "val", value);
		collMapper.getFiller(mapper, testType).fillFromNBT(nbt, "val", obj);
		assertEquals(obj, value);
	}
}

package io.darkcraft.darkcore.nbt.impl.wrappers.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

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
public class CollectionMapperTest
{
	private static final CollectionMapperTestHelper helper = new CollectionMapperTestHelper();
	private static final CollectionMapperImpl impl = new CollectionMapperImpl();

	private NBTMapper mapper;
	private CollectionMapperImpl collMapper;
	private NBTTagCompound nbt;

	@Before
	public void before()
	{
		mapper = NBTHelper.INSTANCE.buildMapper().build();
		collMapper = new CollectionMapperImpl();
		nbt = new NBTTagCompound();
	}

	@Parameters(name= "{0}")
	public static Collection<Object[]> getParameters() throws NoSuchFieldException, SecurityException
	{
		return Arrays.asList(new Object[][] {
			{ CollectionMapperTestHelper.getStrList(), helper.stringList, ArrayList.class },
			{ CollectionMapperTestHelper.getStrSet(), helper.stringSet, HashSet.class },
			{ CollectionMapperTestHelper.getStrHashSet(), helper.stringHashSet, HashSet.class },
			{ CollectionMapperTestHelper.getIntList(), helper.intList, ArrayList.class },
			{ CollectionMapperTestHelper.getIntArrList(), helper.intArrList, ArrayList.class },
			{ CollectionMapperTestHelper.getIntLinkList(), helper.intLinkList, LinkedList.class }
		});
	}

	@Parameter(0)
	public Type testType;

	@Parameter(1)
	public Collection<?> value;

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
		Collection<?> ret = collMapper.<Collection<?>>getReader(mapper, testType).readFromNBT(nbt, "val");
		assertEquals(value, ret);
	}

	@Test
	public void testExpectedClass()
	{
		collMapper.getWriter(mapper, testType).writeToNBT(nbt, "val", value);
		Collection<?> ret = collMapper.<Collection<?>>getReader(mapper, testType).readFromNBT(nbt, "val");
		assertEquals(expectedClass, ret.getClass());
	}
}

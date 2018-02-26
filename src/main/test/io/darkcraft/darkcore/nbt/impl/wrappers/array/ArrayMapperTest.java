package io.darkcraft.darkcore.nbt.impl.wrappers.array;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.junit.Assert;
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
public class ArrayMapperTest
{
	private static final ArrayMapperTestHelper helper = new ArrayMapperTestHelper();

	private NBTMapper mapper;
	private ArrayMapperImpl collMapper;
	private NBTTagCompound nbt;

	@Before
	public void before()
	{
		mapper = NBTHelper.INSTANCE.buildDefaultMapper().build();
		collMapper = new ArrayMapperImpl();
		nbt = new NBTTagCompound();
	}

	@Parameters(name="{0}")
	public static Collection<Object[]> getParameters()
	{
		return Arrays.asList(new Object[][] {
			{ ArrayMapperTestHelper.intArr(), helper.intArr, int[].class },
			{ ArrayMapperTestHelper.intArrArr(), helper.intArrArr, int[][].class },
			{ ArrayMapperTestHelper.strStrStrArr(), helper.strStrStrArr, String[][][].class },
			{ ArrayMapperTestHelper.listStrArr(), helper.listStrArr, List[].class }
		});
	}

	@Parameter(0)
	public Type testType;

	@Parameter(1)
	public Object value;

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
		mapper.getWriter(testType).writeToNBT(nbt, "val", value);
		Object ret = mapper.getReader(testType).readFromNBT(nbt, "val");
		Assert.assertTrue(Objects.deepEquals(value, ret));
	}

	@Test
	public void testExpectedClass()
	{
		mapper.getWriter(testType).writeToNBT(nbt, "val", value);
		Object ret = mapper.getReader(testType).readFromNBT(nbt, "val");
		assertEquals(expectedClass, ret.getClass());
	}
}

package io.darkcraft.darkcore.nbt.impl.wrappers.reflection;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.mapper.NBTFiller.NBTObjFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader.NBTObjReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter.NBTObjWriter;
import io.darkcraft.darkcore.nbt.util.NBTHelper;

public class ReflectionTest
{
	NBTMapper mapper = NBTHelper.INSTANCE.buildDefaultMapper()
			.build();
	NBTObjReader<ReflectionTestHelper> reader;
	NBTObjWriter<ReflectionTestHelper> writer;
	NBTObjFiller<ReflectionTestHelper> filler;

	private ReflectionTestHelper rth;

	@Before
	public void setup()
	{
		rth = new ReflectionTestHelper("s", 1, 2);
		reader = (NBTObjReader<ReflectionTestHelper>) mapper.getReader(ReflectionTestHelper.class);
		writer = (NBTObjWriter<ReflectionTestHelper>) mapper.getWriter(ReflectionTestHelper.class);
		filler = (NBTObjFiller<ReflectionTestHelper>) mapper.getFiller(ReflectionTestHelper.class);
	}

	@Test
	public void test()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writer.writeToNBT(nbt, rth);
		ReflectionTestHelper second = reader.readFromNBT(nbt);
		assertEquals(rth, second);
		System.out.println(reader);
	}
}

package io.darkcraft.darkcore.nbt.impl.wrappers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;
import io.darkcraft.darkcore.nbt.util.NBTHelper;

public class PrimitiveWrappersTest
{
	private final NBTMapper mapper = NBTHelper.INSTANCE.buildDefaultMapper().build();
	private NBTTagCompound nbt;

	@Before
	public void setup()
	{
		nbt = new NBTTagCompound();
	}

	@Test
	public void mapper_Integer()
	{
		NBTWriter<Integer> intWriter = mapper.getWriter(Integer.class);
		NBTReader<Integer> intReader = mapper.getReader(Integer.class);

		intWriter.writeToNBT(nbt, "1", 1);
		assertEquals("Int", Integer.valueOf(1), intReader.readFromNBT(nbt, "1"));

		intWriter.writeToNBT(nbt, "M", 100);
		assertEquals("Int", Integer.valueOf(100), intReader.readFromNBT(nbt, "M"));

		intWriter.writeToNBT(nbt, "NEG", -100);
		assertEquals("Int", Integer.valueOf(-100), intReader.readFromNBT(nbt, "NEG"));

		intWriter.writeToNBT(nbt, "L", 250);
		assertEquals("Int", Integer.valueOf(250), intReader.readFromNBT(nbt, "L"));

		intWriter.writeToNBT(nbt, "MAX", Integer.MAX_VALUE);
		assertEquals("Int", Integer.valueOf(Integer.MAX_VALUE), intReader.readFromNBT(nbt, "MAX"));

		intWriter.writeToNBT(nbt, "MIN", Integer.MIN_VALUE);
		assertEquals("Int", Integer.valueOf(Integer.MIN_VALUE), intReader.readFromNBT(nbt, "MIN"));
	}
}

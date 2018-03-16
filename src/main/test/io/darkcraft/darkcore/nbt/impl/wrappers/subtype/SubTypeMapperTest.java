package io.darkcraft.darkcore.nbt.impl.wrappers.subtype;

import org.junit.Before;
import org.junit.Test;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.impl.wrappers.subtype.SubTypeMapperTestHelper.B;
import io.darkcraft.darkcore.nbt.impl.wrappers.subtype.SubTypeMapperTestHelper.Container;
import io.darkcraft.darkcore.nbt.mapper.NBTFiller;
import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;
import io.darkcraft.darkcore.nbt.util.NBTHelper;

public class SubTypeMapperTest
{
	private NBTMapper mapper;
	NBTTagCompound nbt;
	NBTWriter<Container> writer;
	NBTReader<Container> reader;
	NBTFiller<Container> filler;

	@Before
	public void setup()
	{
		nbt = new NBTTagCompound();
		mapper = NBTHelper.INSTANCE.buildDefaultMapper().build();
		writer = mapper.getWriter(Container.class);
		reader = mapper.getReader(Container.class);
		filler = mapper.getFiller(Container.class);
	}

	@Test
	public void testRegular()
	{
		Container c = new Container();
		c.obj = new B();
		writer.writeToNBT(nbt, "val", c);
		System.out.println(nbt);
	}
}

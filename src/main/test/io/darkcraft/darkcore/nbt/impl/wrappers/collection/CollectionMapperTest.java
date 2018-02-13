package io.darkcraft.darkcore.nbt.impl.wrappers.collection;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import net.minecraft.nbt.NBTTagCompound;

import io.darkcraft.darkcore.nbt.mapper.NBTMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter;
import io.darkcraft.darkcore.nbt.util.NBTHelper;

public class CollectionMapperTest
{
	private static final CollectionMapperImpl impl = new CollectionMapperImpl();

	@Test
	public void testWriterGet() throws NoSuchFieldException, SecurityException
	{
		NBTMapper mapper = NBTHelper.INSTANCE.buildMapper().build();
		NBTWriter<ArrayList<Integer>> writer =
				impl.<ArrayList<Integer>>getWriter(mapper, CollectionMapperTestHelper.getIntArrList());
		ArrayList<Integer> intList = new ArrayList<>(Arrays.asList(1,2,3,4,5));

		NBTTagCompound nbt = new NBTTagCompound();
		writer.writeToNBT(nbt, "List", intList);
		System.out.println();
	}
}

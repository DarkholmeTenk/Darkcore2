package io.darkcraft.darkcore.data.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;

public class DarkcraftWSD extends WorldSavedData
{

	public DarkcraftWSD(String name)
	{
		super(name);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		// TODO Auto-generated method stub
		return null;
	}

}

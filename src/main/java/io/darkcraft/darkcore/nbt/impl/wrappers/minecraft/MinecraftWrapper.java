package io.darkcraft.darkcore.nbt.impl.wrappers.minecraft;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

import io.darkcraft.darkcore.nbt.impl.mapper.helpers.ClassPartialMapper;
import io.darkcraft.darkcore.nbt.mapper.NBTReader.NBTObjReader;
import io.darkcraft.darkcore.nbt.mapper.NBTWriter.NBTObjWriter;

public final class MinecraftWrapper
{
	public static final ClassPartialMapper PARTIAL = new ClassPartialMapper()
			.register(ItemStack.class,
					(NBTObjReader<ItemStack>) ItemStack::new,
					(nbt, id, is) -> nbt.setTag(id, is.serializeNBT()))
			.register(FluidStack.class,
					(NBTObjReader<FluidStack>) FluidStack::loadFluidStackFromNBT,
					(NBTObjWriter<FluidStack>) (nbt,fs)->fs.writeToNBT(nbt))
			.register(BlockPos.class,
					(NBTObjReader<BlockPos>)
							nbt->new BlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z")),
					(NBTObjWriter<BlockPos>)
							(nbt,v)->{
								nbt.setInteger("x", v.getX());
								nbt.setInteger("y", v.getY());
								nbt.setInteger("z", v.getZ());
							})
			.finish();
}

package com.charles445.simpledifficulty.block;

import com.charles445.simpledifficulty.api.SDFluids;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidBasicMixable extends BlockFluidBasic {

	public BlockFluidBasicMixable(Fluid fluid, Material material) {
		super(fluid, material);
	}

	public void checkAndMixBlock(BlockPos pos, World world) {
		Block down = world.getBlockState(pos.down()).getBlock();
		Block up = world.getBlockState(pos.up()).getBlock();
		Block north = world.getBlockState(pos.north()).getBlock();
		Block east = world.getBlockState(pos.east()).getBlock();
		Block south = world.getBlockState(pos.south()).getBlock();
		Block west = world.getBlockState(pos.west()).getBlock();

		if (down.equals(SDFluids.blockSaltWater) ||
				up.equals(SDFluids.blockSaltWater) ||
				north.equals(SDFluids.blockSaltWater) ||
				east.equals(SDFluids.blockSaltWater) ||
				south.equals(SDFluids.blockSaltWater) ||
				west.equals(SDFluids.blockSaltWater)) {
			world.setBlockState(pos, SDFluids.blockSaltWater.getDefaultState());
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighbourPos) {
		checkAndMixBlock(pos, world);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		world.scheduleUpdate(pos, this, tickRate);
		checkAndMixBlock(pos, world);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		checkAndMixBlock(pos, worldIn);
	}
}

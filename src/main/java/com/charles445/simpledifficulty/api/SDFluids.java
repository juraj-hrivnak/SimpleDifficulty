package com.charles445.simpledifficulty.api;

import com.charles445.simpledifficulty.block.BlockFluidBasic;
import net.minecraftforge.fluids.Fluid;

import java.util.LinkedHashMap;
import java.util.Map;

public class SDFluids 
{
	public static final Map<String, Fluid> fluids = new LinkedHashMap<String, Fluid>();
	public static final Map<String, BlockFluidBasic> fluidBlocks = new LinkedHashMap<String, BlockFluidBasic>();
	
	public static Fluid purifiedWater, saltWater;

	public static BlockFluidBasic blockPurifiedWater, blockSaltWater;
}

package com.charles445.simpledifficulty.api;

import com.charles445.simpledifficulty.block.BlockFluidBasic;
import com.charles445.simpledifficulty.block.BlockFluidBasicMixable;
import net.minecraftforge.fluids.Fluid;

import java.util.LinkedHashMap;
import java.util.Map;

public class SDFluids 
{
	public static final Map<String, Fluid> fluids = new LinkedHashMap<>();
	public static final Map<String, BlockFluidBasic> fluidBlocks = new LinkedHashMap<>();
	
	public static Fluid purifiedWater, saltWater;

	public static BlockFluidBasicMixable blockPurifiedWater;
	public static BlockFluidBasic blockSaltWater;
}

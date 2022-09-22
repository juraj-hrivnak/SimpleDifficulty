package com.charles445.simpledifficulty.api;

import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;

import java.util.LinkedHashMap;
import java.util.Map;

public class SDFluids 
{
	public static final Map<String, Fluid> fluids = new LinkedHashMap<String, Fluid>();
	public static final Map<String, BlockFluidBase> fluidBlocks = new LinkedHashMap<String, BlockFluidBase>();
	
	public static Fluid purifiedWater, saltWater;

	public static BlockFluidBase blockPurifiedWater, blockSaltWater;
}

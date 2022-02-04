package com.charles445.simpledifficulty.block;

import com.charles445.simpledifficulty.api.SDFluids;

import com.charles445.simpledifficulty.api.config.ServerConfig;
import com.charles445.simpledifficulty.api.config.ServerOptions;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

public class BlockFluidBasic extends BlockFluidClassic
{
	public BlockFluidBasic(Fluid fluid, Material material)
	{
		super(fluid, material);
		setRegistryName(fluid.getName());
		setUnlocalizedName(this.getRegistryName().toString());
		SDFluids.fluidBlocks.put(fluid.getName(), this);

		displacements.putAll(customDisplacements);
	}

	protected final static Map<Block, Boolean> customDisplacements = Maps.newHashMap();
	static
	{
		if (Loader.isModLoaded("biomesoplenty")) {
			customDisplacements.put(REGISTRY.getObject(new ResourceLocation("biomesoplenty", "coral")), false);
			customDisplacements.put(REGISTRY.getObject(new ResourceLocation("biomesoplenty", "seaweed")), false);
		}
		if (Loader.isModLoaded("backportedflora")) {
			customDisplacements.put(REGISTRY.getObject(new ResourceLocation("backportedflora", "rivergrass")), false);
			customDisplacements.put(REGISTRY.getObject(new ResourceLocation("backportedflora", "seagrass")), false);
			customDisplacements.put(REGISTRY.getObject(new ResourceLocation("backportedflora", "kelp")), false);
		}
		if (Loader.isModLoaded("dynamictreesbop")) {
			customDisplacements.put(REGISTRY.getObject(new ResourceLocation("dynamictreesbop", "rootywater")), false);
		}

		customDisplacements.put(Blocks.WATER, false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
		return super.getFogColor(world, pos, Blocks.WATER.getDefaultState(), entity, originalColor, partialTicks);
	}

	@Override
	@SuppressWarnings("deprecation")
	public int getLightOpacity(IBlockState state) {
		if (ServerConfig.instance.getBoolean(ServerOptions.PURIFIED_WATER_OPACITY)) {
			return 1;
		} else {
			return 3;
		}
	}
}

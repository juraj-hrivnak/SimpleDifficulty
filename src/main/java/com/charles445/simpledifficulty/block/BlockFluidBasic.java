package com.charles445.simpledifficulty.block;

import com.charles445.simpledifficulty.api.SDFluids;
import com.charles445.simpledifficulty.api.config.ServerConfig;
import com.charles445.simpledifficulty.api.config.ServerOptions;
import com.google.common.collect.Maps;
import git.jbredwards.fluidlogged_api.api.block.IFluidloggableFluid;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;
import java.util.Objects;

@Optional.Interface(iface = "git.jbredwards.fluidlogged_api.api.block.IFluidloggableFluid", modid = "fluidlogged_api")
public class BlockFluidBasic extends BlockFluidClassic implements IFluidloggableFluid
{
	public BlockFluidBasic(Fluid fluid, Material material)
	{
		super(fluid, material);
		setRegistryName(fluid.getName());
		setUnlocalizedName(Objects.requireNonNull(this.getRegistryName()).toString());
		SDFluids.fluidBlocks.put(fluid.getName(), this);

		displacements.putAll(customDisplacements);
	}

	protected final static Map<Block, Boolean> customDisplacements = Maps.newHashMap();
	static
	{
		if (Loader.isModLoaded("biomesoplenty"))
		{
			customDisplacements.put(REGISTRY.getObject(new ResourceLocation("biomesoplenty", "coral")), false);
			customDisplacements.put(REGISTRY.getObject(new ResourceLocation("biomesoplenty", "seaweed")), false);
		}
		if (Loader.isModLoaded("backportedflora"))
		{
			customDisplacements.put(REGISTRY.getObject(new ResourceLocation("backportedflora", "rivergrass")), false);
			customDisplacements.put(REGISTRY.getObject(new ResourceLocation("backportedflora", "seagrass")), false);
			customDisplacements.put(REGISTRY.getObject(new ResourceLocation("backportedflora", "kelp")), false);
		}
		if (Loader.isModLoaded("greenery"))
		{
			customDisplacements.put(REGISTRY.getObject(new ResourceLocation("greenery", "rivergrass")), false);
			customDisplacements.put(REGISTRY.getObject(new ResourceLocation("greenery", "seagrass")), false);
			customDisplacements.put(REGISTRY.getObject(new ResourceLocation("greenery", "kelp")), false);
		}
		if (Loader.isModLoaded("dynamictreesbop"))
		{
			customDisplacements.put(REGISTRY.getObject(new ResourceLocation("dynamictreesbop", "rootywater")), false);
		}

		customDisplacements.put(Blocks.WATER, false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks)
	{
		int biomeWaterColor = BiomeColorHelper.getWaterColorAtPos(world, pos);
		Vec3d waterBlockColor = Blocks.WATER.getFogColor(world, pos, Blocks.WATER.getDefaultState(), entity, originalColor, partialTicks);

		float red = (biomeWaterColor >> 16 & 0xFF) / 1655.0F;
		float green = (biomeWaterColor >> 8 & 0xFF) / 655.0F;
		float blue = (biomeWaterColor & 0xFF) / 255.0F;

		return new Vec3d(waterBlockColor.x + red, waterBlockColor.y + green, waterBlockColor.z + blue);
	}

	@Override
	@SuppressWarnings("deprecation")
	public int getLightOpacity(IBlockState state)
	{
		return ServerConfig.instance.getBoolean(ServerOptions.PURIFIED_WATER_OPACITY) ? 1 : 3;
	}

	@Override
	@Optional.Method(modid = "fluidlogged_api")
	public boolean isFluidloggableFluid()
	{
		return true;
	}
}

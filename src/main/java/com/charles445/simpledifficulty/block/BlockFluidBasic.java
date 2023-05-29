package com.charles445.simpledifficulty.block;

import com.charles445.simpledifficulty.api.SDFluids;
import com.charles445.simpledifficulty.api.config.ServerConfig;
import com.charles445.simpledifficulty.api.config.ServerOptions;
import com.google.common.collect.Maps;
import git.jbredwards.fluidlogged_api.api.block.IFluidloggableFluid;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Optional.Interface(iface = "git.jbredwards.fluidlogged_api.api.block.IFluidloggableFluid", modid = "fluidlogged_api")
public class BlockFluidBasic extends BlockFluidClassic implements IFluidloggableFluid
{
	Block ice;

	public BlockFluidBasic(Fluid fluid, Material material, Block ice)
	{
		super(fluid, material);
		this.ice = ice;
		setRegistryName(fluid.getName());
		setTranslationKey(Objects.requireNonNull(this.getRegistryName()).toString());
		SDFluids.fluidBlocks.put(fluid.getName(), this);

		displacements.putAll(customDisplacements);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		super.updateTick(world, pos, state, random);

		Biome biome = world.getBiome(pos);
		float f = biome.getTemperature(pos);

		if (!(f >= 0.15F) && world.getLightFor(EnumSkyBlock.BLOCK, pos) < 10 && state.getValue(LEVEL) == 0)
		{
			for (EnumFacing face : EnumFacing.HORIZONTALS)
			{
				if (world.getBlockState(pos.offset(face)).getBlock() != this)
				{
					world.setBlockState(pos, this.ice.getDefaultState());
					break;
				}
			}
		}
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
	public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles)
	{
		return true;
	}

	@Override
	public boolean addRunningEffects(IBlockState state, World world, BlockPos pos, Entity entity)
	{
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager)
	{
		return true;
	}

	@Override
	@Optional.Method(modid = "fluidlogged_api")
	public boolean isFluidloggableFluid()
	{
		return true;
	}
}

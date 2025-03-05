package com.charles445.simpledifficulty.block;

import com.charles445.simpledifficulty.api.SDBlocks;
import com.charles445.simpledifficulty.api.SDFluids;
import com.charles445.simpledifficulty.api.config.ServerConfig;
import com.charles445.simpledifficulty.api.config.ServerOptions;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sereneseasons.season.SeasonASMHelper;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class BlockFluidBasic extends BlockFluidClassic
{
	private final String iceBlock;

	public BlockFluidBasic(Fluid fluid, Material material, String iceBlock)
	{
		super(fluid, material);
		setRegistryName(fluid.getName());
		setTranslationKey(Objects.requireNonNull(this.getRegistryName()).toString());
		SDFluids.fluidBlocks.put(fluid.getName(), this);

		displacements.putAll(customDisplacements);

		this.iceBlock = iceBlock;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		super.updateTick(world, pos, state, random);

		BlockPos posDown = new BlockPos(pos.getX(), 0, pos.getZ()).up(world.getPrecipitationHeight(pos).getY()).down();

		if (this.canFreeze(world, posDown) && world.rand.nextInt(16) == 0)
		{
			world.setBlockState(posDown, SDBlocks.blocks.get(iceBlock).getDefaultState());
		}
	}

	public boolean canFreeze(World world, BlockPos pos)
	{
		Biome biome = world.getBiome(pos);
		float f = biome.getTemperature(pos);

		if (Loader.isModLoaded("sereneseasons"))
		{
			f = SeasonASMHelper.getFloatTemperature(world, biome, pos);
		}

		if (f <= 0.15F)
		{
			if (pos.getY() >= 0 && pos.getY() < 256 && world.getLightFor(EnumSkyBlock.BLOCK, pos) < 10)
			{
				IBlockState iblockstate1 = world.getBlockState(pos);
				Block block = iblockstate1.getBlock();

				return block == this && iblockstate1.getValue(BlockLiquid.LEVEL) == 0;
			}

		}
		return false;
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
}

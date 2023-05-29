package com.charles445.simpledifficulty.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

import static com.charles445.simpledifficulty.api.SDBlocks.purifiedWaterIce;
import static com.charles445.simpledifficulty.api.SDBlocks.saltWaterIce;
import static com.charles445.simpledifficulty.api.SDFluids.blockPurifiedWater;
import static com.charles445.simpledifficulty.api.SDFluids.blockSaltWater;

public class WorldGenIce implements IWorldGenerator
{
    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if (world.provider.getDimension() == 0)
        {
            for (int x = 0; x < 16; x++)
            {
                for (int z = 0; z < 16; z++)
                {
                    int xoff = chunkX * 16 + 8 + x;
                    int zoff = chunkZ * 16 + 8 + z;
                    BlockPos pos = new BlockPos(xoff, 0, zoff);
                    pos = pos.up(world.getPrecipitationHeight(pos).getY());

                    BlockPos posDown = pos.down();
                    IBlockState stateAt = world.getBlockState(posDown);
                    if (this.canBlockFreeze(blockSaltWater, world, posDown, false) || stateAt.getBlock() == Blocks.ICE)
                    {
                        world.setBlockState(posDown, saltWaterIce.getDefaultState());
                    }

                    if (this.canBlockFreeze(blockPurifiedWater, world, posDown, false) || stateAt.getBlock() == Blocks.ICE)
                    {
                        world.setBlockState(posDown, purifiedWaterIce.getDefaultState());
                    }
                }
            }
        }
    }

    public boolean canBlockFreeze(Block water, World world, BlockPos pos, boolean noWaterAdj)
    {
        Biome biome = world.getBiome(pos);
        float f = biome.getTemperature(pos);

        if (!(f >= 0.15F)) {
            if (pos.getY() >= 0 && pos.getY() < 256 && world.getLightFor(EnumSkyBlock.BLOCK, pos) < 10) {
                IBlockState iblockstate1 = world.getBlockState(pos);
                Block block = iblockstate1.getBlock();

                if (block == water && iblockstate1.getValue(BlockLiquid.LEVEL) == 0) {
                    if (!noWaterAdj) {
                        return true;
                    }

                    boolean flag = this.isWater(water, world, pos.west())
                            && this.isWater(water, world, pos.east())
                            && this.isWater(water, world, pos.north())
                            && this.isWater(water, world, pos.south());

                    return !flag;
                }
            }

        }
        return false;
    }

    private boolean isWater(Block water, World world, BlockPos pos)
    {
        return world.getBlockState(pos).getMaterial() == water.getDefaultState().getMaterial();
    }
}
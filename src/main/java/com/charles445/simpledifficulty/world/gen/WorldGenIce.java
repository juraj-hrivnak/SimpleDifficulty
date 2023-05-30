package com.charles445.simpledifficulty.world.gen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

import static com.charles445.simpledifficulty.api.SDBlocks.icePurifiedWater;
import static com.charles445.simpledifficulty.api.SDBlocks.iceSaltWater;
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
                    if (blockSaltWater.canFreeze(world, posDown) || stateAt.getBlock() == Blocks.ICE)
                    {
                        world.setBlockState(posDown, iceSaltWater.getDefaultState());
                    }

                    if (blockPurifiedWater.canFreeze(world, posDown) || stateAt.getBlock() == Blocks.ICE)
                    {
                        world.setBlockState(posDown, icePurifiedWater.getDefaultState());
                    }
                }
            }
        }
    }
}
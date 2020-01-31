package com.charles445.simpledifficulty.util.internal;

import javax.annotation.Nullable;

import com.charles445.simpledifficulty.api.SDCapabilities;
import com.charles445.simpledifficulty.api.SDFluids;
import com.charles445.simpledifficulty.api.SDPotions;
import com.charles445.simpledifficulty.api.config.QuickConfig;
import com.charles445.simpledifficulty.api.config.ServerConfig;
import com.charles445.simpledifficulty.api.config.ServerOptions;
import com.charles445.simpledifficulty.api.thirst.IThirstCapability;
import com.charles445.simpledifficulty.api.thirst.IThirstUtil;
import com.charles445.simpledifficulty.api.thirst.ThirstEnum;
import com.charles445.simpledifficulty.api.thirst.ThirstEnumBlockPos;
import com.charles445.simpledifficulty.api.thirst.ThirstUtil;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class ThirstUtilInternal implements IThirstUtil
{
	//Returns an object based on what is being drunk
	//Not API visible
	@Nullable
	public static ThirstEnumBlockPos traceWaterToDrink(EntityPlayer player)
	{
		if(player.getHeldItemMainhand().isEmpty())
		{
			IThirstCapability capability = SDCapabilities.getThirstData(player);
			if(capability.isThirsty())
			{
				//Empty handed and thirsty
				ThirstEnumBlockPos traceResult = ThirstUtil.traceWater(player);
				if(traceResult==null)
					return null;
				
				if(traceResult.thirstEnum == ThirstEnum.PURIFIED)
				{
					if(ServerConfig.instance.getBoolean(ServerOptions.THIRST_DRINK_BLOCKS))
						player.world.setBlockToAir(traceResult.pos);
					else
						return null;
				}
				else if(traceResult.thirstEnum == ThirstEnum.RAIN && !ServerConfig.instance.getBoolean(ServerOptions.THIRST_DRINK_RAIN))
				{
					return null;
				}
				else if(traceResult.thirstEnum == ThirstEnum.NORMAL && !ServerConfig.instance.getBoolean(ServerOptions.THIRST_DRINK_BLOCKS))
				{
					return null;
				}
				
				return traceResult;
			}
		}
		
		return null;
	}
	
	//API
	
	//Returns an object based on what is being looked at
	@Nullable
	@Override
	public ThirstEnumBlockPos traceWater(EntityPlayer player)
	{	
		//Check if player is looking up, if it's raining, if they can see sky, and if THIRST_DRINK_RAIN is enabled
		//This essentially means rain can't be a trace result for drinking or for a canteen
		
		if(player.rotationPitch < -75.0f && player.world.isRainingAt(player.getPosition()) && player.world.canSeeSky(player.getPosition()) && ServerConfig.instance.getBoolean(ServerOptions.THIRST_DRINK_RAIN))
		{
			//Drinking rain
			return new ThirstEnumBlockPos(ThirstEnum.RAIN, player.getPosition());
		}
		
		//Handle ray tracing
		
		//Get the player's reach distance attribute and cut it in half
		double reach = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue() * 0.5d;
		
		//Similar to Entity.rayTrace
		Vec3d eyevec = player.getPositionEyes(1.0f);
		Vec3d lookvec = player.getLook(1.0f);
		Vec3d targetvec = eyevec.addVector(lookvec.x * reach, lookvec.y * reach, lookvec.z * reach);
		
		//Ray trace from the player's eyepos to where they are looking, and stop at liquids
		RayTraceResult trace = player.getEntityWorld().rayTraceBlocks(eyevec, targetvec, true);
		
		if(trace==null || trace.typeOfHit != RayTraceResult.Type.BLOCK)
			return null;
		
		//Hit a block
		//TODO is there a better way to do this?
		Block traceBlock = player.getEntityWorld().getBlockState(trace.getBlockPos()).getBlock();
		if(traceBlock == Blocks.WATER)
		{
			return new ThirstEnumBlockPos(ThirstEnum.NORMAL, trace.getBlockPos());
		}
		else if(traceBlock == SDFluids.blockPurifiedWater)
		{
			return new ThirstEnumBlockPos(ThirstEnum.PURIFIED, trace.getBlockPos());
		}
		
		return null;
	}
	
	@Override
	public void takeDrink(EntityPlayer player, int thirst, float saturation, float dirtyChance)
	{
		if(!QuickConfig.isThirstEnabled())
			return;
		
		IThirstCapability capability = SDCapabilities.getThirstData(player);
		
		//TODO reconsider the check for isThirsty, because modders will possibly want to add saturation themselves
		//OK but the check is here because otherwise people can just chug water bottles to fortify saturation
		//And other unforseeable futures
		//Maybe make another method callable to ignore this check... lmao
		
		if(capability.isThirsty())
		{
			capability.addThirstLevel(thirst);
			
			//In TAN, any drink with a hydration higher than 0.5f will be more beneficial to you if you drink it when you're not as thirsty
			
			//In this mod, saturation is half as powerful and capped at 1.0f
			//Because of that, there is also no need to check the player's current thirst level
			//There should be no saturation advantageous time to drink anything
			//Safety checks are done in addThirstSaturation itself
			
			capability.addThirstSaturation(Math.min(1.0f, saturation) * thirst);
			
			//Test for dirtiness
			if(dirtyChance != 0.0f && player.world.rand.nextFloat() < dirtyChance)
			{
				player.addPotionEffect(new PotionEffect(SDPotions.thirsty,600));
			}
		}
	}
	
	@Override
	public void takeDrink(EntityPlayer player, int thirst, float saturation)
	{
		//Clean water
		takeDrink(player, thirst, saturation, 0.0f);
	}
	
	@Override
	public void takeDrink(EntityPlayer player, ThirstEnum type)
	{
		takeDrink(player, type.getThirst(), type.getSaturation(), type.getDirtyChance());
	}
	
	@Override
	public ItemStack createPurifiedWaterBucket()
	{
		return FluidUtil.getFilledBucket(new FluidStack(SDFluids.purifiedWater, Fluid.BUCKET_VOLUME));
	}
}
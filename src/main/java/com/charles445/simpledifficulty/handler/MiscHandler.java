package com.charles445.simpledifficulty.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MiscHandler
{
	//Event handler for miscellaneous things that come up
	
	//Fix stupid jumping vanilla glitch
	@SubscribeEvent
	public void onDismount(EntityMountEvent event)
	{
		if(event.isDismounting() && event.getEntityMounting() instanceof EntityPlayer)
		{
			((EntityPlayer) event.getEntityMounting()).setJumping(false);
		}
	}
	
	//Prevent canteens from being repaired in the anvil
	//Canteens don't use damage anymore so this is no longer necessary
	/*
	@SubscribeEvent
	public void onAnvilUpdate(AnvilUpdateEvent event)
	{
		if(event.getLeft().getItem() == SDItems.canteen || event.getRight().getItem() == SDItems.canteen)
			event.setOutput(ItemStack.EMPTY);
	}
	*/
	
	
	
	//TODO Feature to have glass bottles remove Material.WATER blocks as a config option?
	//Doesn't work yet
	/*
	@SubscribeEvent
	public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
	{
		//Both Sides
		if(event.getItemStack().getItem()==Items.GLASS_BOTTLE)
		{
			ThirstEnumBlockPos traceResult = ThirstUtil.traceWater(event.getEntityPlayer());
			if(traceResult!=null && traceResult.thirstEnum == ThirstEnum.NORMAL)
			{
				//Glass bottle, found water, verify it's water block
				World world = event.getWorld();
				Material tracedMaterial = event.getWorld().getBlockState(traceResult.pos).getMaterial();
				if(tracedMaterial==Material.WATER)
				{
					world.setBlockToAir(traceResult.pos);
				}
			}
		}
		
	}
	*/
}

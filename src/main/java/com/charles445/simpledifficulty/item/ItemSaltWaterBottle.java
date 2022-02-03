package com.charles445.simpledifficulty.item;

import com.charles445.simpledifficulty.api.thirst.ThirstEnum;
import net.minecraft.item.ItemStack;

public class ItemSaltWaterBottle extends ItemDrinkBase
{
	@Override
	public int getThirstLevel(ItemStack stack)
	{
		return ThirstEnum.SALT.getThirst();
	}

	@Override
	public float getSaturationLevel(ItemStack stack)
	{
		return ThirstEnum.SALT.getSaturation();
	}

	@Override
	public float getDirtyChance(ItemStack stack)
	{
		return ThirstEnum.SALT.getThirstyChance();
	}
}

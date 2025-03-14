package com.charles445.simpledifficulty.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

public class OreDictUtil
{
	public static final NonNullList<ItemStack> listAlljuice = OreDictionary.getOres("listAlljuice");
	public static final NonNullList<ItemStack> listAllsmoothie = OreDictionary.getOres("listAllsmoothie");
	public static final NonNullList<ItemStack> listAllsoda = OreDictionary.getOres("listAllsoda");
	
	public static final NonNullList<ItemStack> logWood = OreDictionary.getOres("logWood");
	public static final NonNullList<ItemStack> stick = OreDictionary.getOres("stickWood");
	
	public static boolean isOre(NonNullList<ItemStack> stackList, ItemStack stackCheck)
	{
		return OreDictUtil.containsMatch(false,stackList,stackCheck);
	}
	
	public static boolean containsMatch(boolean strict, NonNullList<ItemStack> inputs, @Nonnull ItemStack... targets)
	{
		for (ItemStack input : inputs)
		{
			for (ItemStack target : targets)
			{
				//SWAPPED INPUT AND TARGET
				//Why is it like this
				if (OreDictionary.itemMatches(input, target, strict))
				{
					return true;
				}
			}
		}
		return false;
	}
}

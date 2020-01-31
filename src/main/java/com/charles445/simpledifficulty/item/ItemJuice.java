package com.charles445.simpledifficulty.item;

import com.charles445.simpledifficulty.SimpleDifficulty;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemJuice extends ItemDrinkBase
{
	public ItemJuice()
	{
		super();
		this.setHasSubtypes(true);
	}
	
	@Override
	public void runSecondaryEffect(EntityPlayer player, ItemStack stack)
	{
		//TODO consider if this is a good idea and make configurable
		JuiceEnum type = getEnumForStack(stack);
		if(type == JuiceEnum.GOLDEN_APPLE)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 1));
			player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2400, 0));
		}
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (this.isInCreativeTab(tab))
		{
			//items.add(new ItemStack(this));
			for(JuiceEnum juice: JuiceEnum.values())
			{
				items.add(new ItemStack(this, 1, juice.ordinal()));
			}
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return "item."+SimpleDifficulty.MODID+":"+"juice_"+getEnumForStack(stack).toString();
	}
	
	@Override
	public int getThirstLevel(ItemStack stack)
	{
		return getEnumForStack(stack).getThirstLevel();
	}

	@Override
	public float getSaturationLevel(ItemStack stack)
	{
		return getEnumForStack(stack).getSaturation();
	}

	@Override
	public float getDirtyChance(ItemStack stack)
	{
		return getEnumForStack(stack).getDirtyChance();
	}
	
	private JuiceEnum getEnumForStack(ItemStack stack)
	{
		if(stack.getMetadata()>=JuiceEnum.values().length)
			return JuiceEnum.values()[0];
		
		return JuiceEnum.values()[stack.getMetadata()];
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		//This is just the visual enchantment effect
		JuiceEnum type = getEnumForStack(stack);
		if(type == JuiceEnum.GOLDEN_APPLE || type == JuiceEnum.GOLDEN_CARROT || type == JuiceEnum.GOLDEN_MELON)
			return true;
		return super.hasEffect(stack);
	}
	
	public static enum JuiceEnum
	{
		APPLE(				8,	0.8f),	//6.4
		BEETROOT(			10,	0.8f),	//8
		CACTUS(				9,	0.3f),	//2.7
		CARROT(				8,	0.6f),	//4.8
		CHORUS_FRUIT(		12,	0.6f),	//7.2
		GOLDEN_APPLE(		20,	1.0f),	//20
		GOLDEN_CARROT(		14,	1.0f),	//14
		GOLDEN_MELON(		16,	1.0f),	//16
		MELON(				8,	0.5f),	//4
		PUMPKIN(			7,	0.7f);	//4.9
		
		private int thirst;
		private float saturation;
		
		public int getThirstLevel()
		{
			return thirst;
		}
		
		public float getSaturation()
		{
			return saturation;
		}
		
		public float getDirtyChance()
		{
			return 0.0f;
		}
		
		private JuiceEnum(int thirst, float saturation)
		{
			this.thirst=thirst;
			this.saturation=saturation;
		}
		
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}

	
}
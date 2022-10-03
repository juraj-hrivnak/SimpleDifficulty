package com.charles445.simpledifficulty.potion;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionHypothermia extends PotionThermia
{
	private final ResourceLocation texture;
	
	public PotionHypothermia()
	{
		super(true, 0x5CEBFF);
		xOffset = 0;
		yOffset = 0;
		texture = formatTexture("hypothermia");
	}
	
	@Override
	public ResourceLocation getTexture()
	{
		return texture;
	}

	@Override
	public void attackPlayer(EntityPlayer player, float damage, int amplifier)
	{
		if (amplifier >= 4 && amplifier < 6)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 405, 0));
			player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 405, 0));
		}
		else if (amplifier >= 6 && amplifier < 8)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 405, 1));
			player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 405, 1));
		}
		else if (amplifier >= 8)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 405, 2));
			player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 405, 2));
		}
	}
}

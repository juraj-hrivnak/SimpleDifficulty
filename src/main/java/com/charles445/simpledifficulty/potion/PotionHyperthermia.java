package com.charles445.simpledifficulty.potion;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionHyperthermia extends PotionThermia
{
	private final ResourceLocation texture;
	
	public PotionHyperthermia()
	{
		super(true, 0xFFC85C);
		xOffset = 0;
		yOffset = 0;
		texture = formatTexture("hyperthermia");
	}

	@Override
	public ResourceLocation getTexture()
	{
		return texture;
	}
	
	@Override
	public void attackPlayer(EntityPlayer player, float damage, int amplifier)
	{
		if (amplifier >= 4)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 405, 0));
		}
	}
}

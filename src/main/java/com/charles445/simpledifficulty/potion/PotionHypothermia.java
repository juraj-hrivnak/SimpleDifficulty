package com.charles445.simpledifficulty.potion;

import com.charles445.simpledifficulty.api.SDDamageSources;
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
		if (player.getMaxHealth() < 4.0F && player.getHealth() > 1.0F)
		{
			player.attackEntityFrom(SDDamageSources.HYPOTHERMIA, 1.0F);
			player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 300, 1));
			player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 300, 1));
		}
	}
}

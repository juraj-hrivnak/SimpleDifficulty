package com.charles445.simpledifficulty.potion;

import net.minecraft.entity.player.EntityPlayer;
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
	public void attackPlayer(EntityPlayer player, float damage)
	{

	}
}

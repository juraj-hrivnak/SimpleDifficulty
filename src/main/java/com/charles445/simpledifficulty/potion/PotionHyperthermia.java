package com.charles445.simpledifficulty.potion;

import com.charles445.simpledifficulty.api.SDDamageSources;
import net.minecraft.entity.player.EntityPlayer;
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
	public void attackPlayer(EntityPlayer player, float damage)
	{
		if (player.getHealth() > 1.0F)
		{
			player.attackEntityFrom(SDDamageSources.HYPERTHERMIA, damage);
		}
	}
}

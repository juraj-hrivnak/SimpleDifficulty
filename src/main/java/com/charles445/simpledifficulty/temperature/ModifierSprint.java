package com.charles445.simpledifficulty.temperature;

import com.charles445.simpledifficulty.config.ModConfig;
import net.minecraft.entity.player.EntityPlayer;

public class ModifierSprint extends ModifierBase
{
	public ModifierSprint()
	{
		super("Sprint");
	}
	
	@Override
	public float getPlayerInfluence(EntityPlayer player)
	{
		if(player.isSprinting())
			return ModConfig.server.temperature.sprintingValue;
		else
			return 0.0f;
	}
}

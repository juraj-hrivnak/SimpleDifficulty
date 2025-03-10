package com.charles445.simpledifficulty.compat.mod;

import com.charles445.simpledifficulty.SimpleDifficulty;
import com.charles445.simpledifficulty.api.SDCapabilities;
import com.charles445.simpledifficulty.api.config.QuickConfig;
import com.charles445.simpledifficulty.config.ModConfig;
import com.charles445.simpledifficulty.util.CompatUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class OreExcavationHandler
{
	private Class EventExcavate;
	private Class MiningAgent;
	
	private Method m_EventExcavate_getAgent;
	
	private Field f_MiningAgent_player; //EntityPlayerMP
	
	private boolean working;
	
	public OreExcavationHandler()
	{
		working = true;
		
		try
		{
			EventExcavate = Class.forName("oreexcavation.events.EventExcavate");
			MiningAgent = Class.forName("oreexcavation.handlers.MiningAgent");
			m_EventExcavate_getAgent = EventExcavate.getDeclaredMethod("getAgent");
			f_MiningAgent_player = MiningAgent.getDeclaredField("player");
			
			Class<?> c_Break = Class.forName("oreexcavation.events.EventExcavate$Break");
			
			boolean sub = CompatUtil.subscribeEventManually(c_Break, this, this.getClass().getDeclaredMethod("onBreak", Object.class));
			
			if(sub)
			{
				SimpleDifficulty.logger.info("OreExcavationHandler Manual SubscribeEvent Success");
			}
			else
			{
				SimpleDifficulty.logger.error("OreExcavationHandler Manual SubscribeEvent Failed");
			}
		}
		catch(Exception e)
		{
			SimpleDifficulty.logger.error("OreExcavationHandler failed to start", e);
			working = false;
		}
		
	}
	
	@SubscribeEvent
	public void onBreak(Object event)
	{
		//DebugUtil.startTimer();
		
		//Seems to take 1.5k on a good day
		
		if(!QuickConfig.isThirstEnabled() || !ModConfig.server.compatibility.toggles.oreExcavation || !working)
			return;
		
		try
		{
			//MiningAgent.cast(     EventExcavate.cast(
			Object o = f_MiningAgent_player.get(m_EventExcavate_getAgent.invoke(event));
			if(o instanceof EntityPlayerMP)
			{
				EntityPlayerMP player = (EntityPlayerMP)o;
				if(player.getEntityWorld().isRemote)
					return;
				
				//Server Side
				if(!shouldSkipThirst(player))
				{
					SDCapabilities.getThirstData(player).addThirstExhaustion((float)ModConfig.server.thirst.thirstBreakBlock);
					//DebugUtil.stopTimer(false);
				}
			}
		}
		catch(Exception e)
		{
			SimpleDifficulty.logger.error("OreExcavationHandler encountered an error, disabling self to avoid error spam.", e);
			working = false;
		}
		
		
	}
	
	private boolean shouldSkipThirst(EntityPlayer player)
	{
		return player.isCreative() || player.isSpectator();
	}
}

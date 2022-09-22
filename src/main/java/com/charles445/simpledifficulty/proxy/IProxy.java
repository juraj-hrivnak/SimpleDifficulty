package com.charles445.simpledifficulty.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

public interface IProxy 
{
	public void preInit();
	
	public void init();
	
	public void postInit();
	
	public Side getSide();
	
	@Nullable
	public EntityPlayer getClientMinecraftPlayer();
	
	@Nullable
	public Boolean isClientConnectedToServer();
	
	public void spawnClientParticle(World world, String type, double xPos, double yPos, double zPos, double motionX, double motionY, double motionZ);
}

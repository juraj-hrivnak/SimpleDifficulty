package com.charles445.simpledifficulty.proxy;

import com.charles445.simpledifficulty.SimpleDifficulty;
import com.charles445.simpledifficulty.api.temperature.ITemperatureCapability;
import com.charles445.simpledifficulty.api.temperature.TemperatureRegistry;
import com.charles445.simpledifficulty.api.temperature.TemperatureUtil;
import com.charles445.simpledifficulty.api.thirst.IThirstCapability;
import com.charles445.simpledifficulty.api.thirst.ThirstUtil;
import com.charles445.simpledifficulty.capability.TemperatureCapability;
import com.charles445.simpledifficulty.capability.TemperatureStorage;
import com.charles445.simpledifficulty.capability.ThirstCapability;
import com.charles445.simpledifficulty.capability.ThirstStorage;
import com.charles445.simpledifficulty.compat.CompatController;
import com.charles445.simpledifficulty.config.JsonConfigInternal;
import com.charles445.simpledifficulty.config.ModConfig;
import com.charles445.simpledifficulty.handler.*;
import com.charles445.simpledifficulty.temperature.*;
import com.charles445.simpledifficulty.util.internal.TemperatureUtilInternal;
import com.charles445.simpledifficulty.util.internal.ThirstUtilInternal;
import com.charles445.simpledifficulty.world.gen.WorldGenIce;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class CommonProxy implements IProxy
{
	//TODO migrate all of this to the main mod class? It might as well not be here
	//Or just hook up stuff with annotations like forge appears to want
	
	@Override
	public void preInit()
	{
		//Setup utility
		TemperatureUtil.internal = new TemperatureUtilInternal();
		ThirstUtil.internal = new ThirstUtilInternal();
		
		//Setup initial configurations
		ModConfig.sendLocalServerConfigToAPI();
		ModConfig.sendLocalClientConfigToAPI();
		
		//Register Capabilities
		CapabilityManager.INSTANCE.register(ITemperatureCapability.class, new TemperatureStorage(), TemperatureCapability::new);
		CapabilityManager.INSTANCE.register(IThirstCapability.class, new ThirstStorage(), ThirstCapability::new);
		
		//Register Mod Handlers
		MinecraftForge.EVENT_BUS.register(new BlockHandler());
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		MinecraftForge.EVENT_BUS.register(new ConfigHandler());
		MinecraftForge.EVENT_BUS.register(new MiscHandler());
		MinecraftForge.EVENT_BUS.register(new TemperatureHandler());
		MinecraftForge.EVENT_BUS.register(new ThirstHandler());
		MinecraftForge.EVENT_BUS.register(new FluidHandler());

		//Populate TemperatureRegistry
		TemperatureRegistry.registerModifier(new ModifierDefault());
		TemperatureRegistry.registerModifier(new ModifierAltitude());
		TemperatureRegistry.registerModifier(new ModifierArmor());
		TemperatureRegistry.registerModifier(new ModifierBiome());
		TemperatureRegistry.registerModifier(new ModifierBlocksTiles());
		TemperatureRegistry.registerModifier(new ModifierDimension());
		TemperatureRegistry.registerModifier(new ModifierHeldItems());
		TemperatureRegistry.registerModifier(new ModifierSnow());
		TemperatureRegistry.registerModifier(new ModifierSprint());
		TemperatureRegistry.registerModifier(new ModifierTemporary());
		TemperatureRegistry.registerModifier(new ModifierTime());
		TemperatureRegistry.registerModifier(new ModifierWet());
		
		//Setup JSON Configurations PreInit
		JsonConfigInternal.preInit(SimpleDifficulty.jsonDirectory);
		
	}
	
	@Override
	public void init()
	{
		//Register Ice Generator
		GameRegistry.registerWorldGenerator(new WorldGenIce(), 0);
	}
	
	@Override
	public void postInit()
	{
		//Setup JSON Configurations
		JsonConfigInternal.postInit(SimpleDifficulty.jsonDirectory);
		
		//Setup Mod Compatibility
		CompatController.setupCommonPostInit();
	}
	
	@Override
	public void spawnClientParticle(World world, String type, double xPos, double yPos, double zPos, double motionX, double motionY, double motionZ)
	{
		
	}
}

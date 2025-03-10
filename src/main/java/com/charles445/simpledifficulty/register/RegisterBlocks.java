package com.charles445.simpledifficulty.register;

import com.charles445.simpledifficulty.SimpleDifficulty;
import com.charles445.simpledifficulty.api.SDBlocks;
import com.charles445.simpledifficulty.block.*;
import com.charles445.simpledifficulty.tileentity.TileEntitySpit;
import com.charles445.simpledifficulty.tileentity.TileEntityTemperature;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import static com.charles445.simpledifficulty.api.SDBlocks.*;

public class RegisterBlocks
{
	@Mod.EventBusSubscriber(modid = SimpleDifficulty.MODID)
	public static class Registrar
	{
		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event)
		{
			final IForgeRegistry<Block> registry = event.getRegistry();
			
			campfire = registerAs("campfire", new BlockCampfire(), registry);
			rainCollector = registerAs("rain_collector", new BlockRainCollector(), registry);
			
			heater = registerAs("heater", new BlockTemperature(1.0f), registry);
			chiller = registerAs("chiller", new BlockTemperature(-1.0f), registry);
			
			spit = registerAs("spit", new BlockSpit(), registry);

			icePurifiedWater = registerAs("purifiedwater_ice", new BlockIceBasic("purifiedwater"), registry);
			iceSaltWater = registerAs("saltwater_ice", new BlockIceBasic("saltwater"), registry);

			//Tile Entities
			GameRegistry.registerTileEntity(TileEntitySpit.class, new ResourceLocation(SimpleDifficulty.MODID, "campfirespit"));
			GameRegistry.registerTileEntity(TileEntityTemperature.class, new ResourceLocation(SimpleDifficulty.MODID,"temperatureChanged"));
			
		}
		
		@SubscribeEvent
		public static void registerItemBlocks(RegistryEvent.Register<Item> event)
		{
			final IForgeRegistry<Item> registry = event.getRegistry();
			
			registerItemBlock(campfire, registry);
			registerItemBlock(rainCollector, registry);
			registerItemBlock(heater, registry);
			registerItemBlock(chiller, registry);
			registerItemBlock(spit, registry);
			registerItemBlock(icePurifiedWater, registry);
			registerItemBlock(iceSaltWater, registry);
		}
		
		private static void registerItemBlock(Block block,  IForgeRegistry<Item> registry)
		{
			registry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		}
		
		private static Block registerAs(String name, final Block newBlock, IForgeRegistry<Block> registry)
		{
			newBlock.setRegistryName(SimpleDifficulty.MODID, name);
			newBlock.setTranslationKey(newBlock.getRegistryName().toString());
			newBlock.setCreativeTab(ModCreativeTab.instance);
			registry.register(newBlock);
			
			//Add to generic block map with settings
			SDBlocks.blocks.put(name, newBlock);

			return newBlock;
		}
		
	}
}

package com.charles445.simpledifficulty.api;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;

import java.util.LinkedHashMap;
import java.util.Map;

public class SDPotions
{
	public static final Map<String, Potion> potions = new LinkedHashMap<String, Potion>();
	public static final Map<String, PotionType> potionTypes = new LinkedHashMap<String, PotionType>();
	
	public static Potion hyperthermia;
	public static Potion hypothermia;
	public static Potion thirsty;
	public static Potion parasites;
	
	public static Potion cold_resist;
	public static Potion heat_resist;
	
	public static PotionType cold_resist_type;
	public static PotionType long_cold_resist_type;
	public static PotionType heat_resist_type;
	public static PotionType long_heat_resist_type;
	
}

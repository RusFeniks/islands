package xyz.pixelatedw.islands.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;

public class WeightConfig
{
	private static WeightConfig instance;

	private HashMap<ResourceLocation, IntValue> islandBiomesWeight;
	private HashMap<ResourceLocation, IntValue> oceanBiomesWeight;
	
	public static void init()
	{
		Pair<WeightConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(WeightConfig::new);
		ForgeConfigSpec configSpec = pair.getRight();
		WeightConfig.instance = pair.getLeft();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, configSpec, "islands-weights.toml");
	}
	
	public static WeightConfig instance()
	{
		return instance;
	}

	public WeightConfig(ForgeConfigSpec.Builder builder)
	{
		builder.comment("List of biomes and their weights, by default everything is set to 5 with a range of 1 - 10 as integers."
			+ "\nThe list will also display modded biomes however it will not remove them once said mod is removed! The line(s) for the mod's biomes will not have any effect however"
			+ "\n\nTIP: Use CTRL+F (Find) to easily find the biome you're looking for")
		.push("Island Biomes Weights");
		{
			this.islandBiomesWeight = new HashMap<ResourceLocation, IntValue>();
			Predicate<Biome> isNotOceanPredicate = (biome) -> biome.getCategory() != Category.OCEAN;
			ArrayList<Biome> list = (ArrayList<Biome>) new ArrayList<Biome>(ForgeRegistries.BIOMES.getValues()).stream().filter(isNotOceanPredicate).collect(Collectors.toList());
			for(Biome biome : list)
			{
				this.islandBiomesWeight.put(biome.getRegistryName(), builder.defineInRange(biome.getRegistryName().toString(), 5, 1, 10));
			}
		}
		builder.pop();
		
		builder.comment("List of biomes and their weights, by default everything is set to 5 with a range of 1 - 10 as integers."
			+ "\nThe list will also display modded biomes however it will not remove them once said mod is removed! The line(s) for the mod's biomes will not have any effect however"
			+ "\nTIP: Use CTRL+F (Find) to easily find the biome you're looking for")
		.push("Ocean Biomes Weights");
		{
			this.oceanBiomesWeight = new HashMap<ResourceLocation, IntValue>();
			Predicate<Biome> isOceanPredicate = (biome) -> biome.getCategory() == Category.OCEAN;
			ArrayList<Biome> list = (ArrayList<Biome>) new ArrayList<Biome>(ForgeRegistries.BIOMES.getValues()).stream().filter(isOceanPredicate).collect(Collectors.toList());
			for(Biome biome : list)
			{
				this.oceanBiomesWeight.put(biome.getRegistryName(), builder.defineInRange(biome.getRegistryName().toString(), 5, 1, 10));
			}			
		}
		builder.pop();
	}
	
	public int getIslandBiomeWeight(ResourceLocation key)
	{
		if(this.islandBiomesWeight.containsKey(key))
			return this.islandBiomesWeight.get(key).get();
		return 5;
	}
	
	public int getOceanBiomeWeight(ResourceLocation key)
	{
		if(this.oceanBiomesWeight.containsKey(key))
			return this.oceanBiomesWeight.get(key).get();
		return 5;	
	}
}

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
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;

public class IslandsWeightConfig
{
	private static IslandsWeightConfig instance;

	private HashMap<ResourceLocation, DoubleValue> islandBiomesWeight;

	public static void init()
	{
		Pair<IslandsWeightConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(IslandsWeightConfig::new);
		ForgeConfigSpec configSpec = pair.getRight();
		IslandsWeightConfig.instance = pair.getLeft();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, configSpec, "islands-islandsweights.toml");
	}
	
	public static IslandsWeightConfig instance()
	{
		return instance;
	}

	public IslandsWeightConfig(ForgeConfigSpec.Builder builder)
	{
		builder.comment("List of biomes and their weights, by default everything is set to 1 with a range of 0.1 - 100 as decimals."
			+ "\nThe list will also display modded biomes however it will not remove them once said mod is removed! The line(s) for the mod's biomes will not have any effect however"
			+ "\nYou can use a reasonable number of digits after the decimal point, however it cannot go under 0.1 (meaning 0.09 is NOT a valid option but 0.11 is a valid option)"
			+ "\n\nTIP: Use CTRL+F (Find) to easily find the biome you're looking for")
		.push("Island Biomes Weights");
		{
			this.islandBiomesWeight = new HashMap<ResourceLocation, DoubleValue>();
			Predicate<Biome> isNotOceanPredicate = (biome) -> biome.getCategory() != Category.OCEAN;
			ArrayList<Biome> list = (ArrayList<Biome>) new ArrayList<Biome>(ForgeRegistries.BIOMES.getValues()).stream().filter(isNotOceanPredicate).collect(Collectors.toList());
			for(Biome biome : list)
			{
				this.islandBiomesWeight.put(biome.getRegistryName(), builder.defineInRange(biome.getRegistryName().toString(), 1, 0.1, 100));
			}
		}
		builder.pop();
	}
	
	public double getIslandBiomeWeight(ResourceLocation key)
	{
		if(this.islandBiomesWeight.containsKey(key))
			return this.islandBiomesWeight.get(key).get();
		return 1;
	}
}

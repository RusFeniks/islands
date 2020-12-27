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

public class OceansWeightConfig
{
	private static OceansWeightConfig instance;

	private HashMap<ResourceLocation, DoubleValue> oceanBiomesWeight;

	public static void init()
	{
		Pair<OceansWeightConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(OceansWeightConfig::new);
		ForgeConfigSpec configSpec = pair.getRight();
		OceansWeightConfig.instance = pair.getLeft();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, configSpec, "islands-oceansweights.toml");
	}
	
	public static OceansWeightConfig instance()
	{
		return instance;
	}

	public OceansWeightConfig(ForgeConfigSpec.Builder builder)
	{		
		builder.comment("List of biomes and their weights, by default everything is set to 1 with a range of 0.1 - 100 as decimals."
			+ "\nThe list will also display modded biomes however it will not remove them once said mod is removed! The line(s) for the mod's biomes will not have any effect however"
			+ "\nYou can use a reasonable number of digits after the decimal point, however it cannot go under 0.1 (meaning 0.09 is NOT a valid option but 0.11 is a valid option)"
			+ "\nTIP: Use CTRL+F (Find) to easily find the biome you're looking for")
		.push("Ocean Biomes Weights");
		{
			this.oceanBiomesWeight = new HashMap<ResourceLocation, DoubleValue>();
			Predicate<Biome> isOceanPredicate = (biome) -> biome.getCategory() == Category.OCEAN;
			ArrayList<Biome> list = (ArrayList<Biome>) new ArrayList<Biome>(ForgeRegistries.BIOMES.getValues()).stream().filter(isOceanPredicate).collect(Collectors.toList());
			for(Biome biome : list)
			{
				this.oceanBiomesWeight.put(biome.getRegistryName(), builder.defineInRange(biome.getRegistryName().toString(), 1, 0.1, 100));
			}			
		}
		builder.pop();
	}
	
	public double getOceanBiomeWeight(ResourceLocation key)
	{
		if(this.oceanBiomesWeight.containsKey(key))
			return this.oceanBiomesWeight.get(key).get();
		return 1;	
	}
}

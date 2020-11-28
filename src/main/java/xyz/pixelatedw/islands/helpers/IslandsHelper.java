package xyz.pixelatedw.islands.helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import xyz.pixelatedw.islands.config.CommonConfig;

public class IslandsHelper
{	
	private static WeightedList<Biome> islandBiomesList = new WeightedList<Biome>();
	private static WeightedList<Biome> oceanBiomesList = new WeightedList<Biome>();

	public IslandsHelper()
	{
		List<String> bannedIslandBiomes = CommonConfig.instance().getBannedIslandsBiomes();	
		Predicate<Biome> islandsIgnorePredicate = (biome) -> !bannedIslandBiomes.contains(biome.getRegistryName().toString());
		for(Biome biome : new ArrayList<Biome>(ForgeRegistries.BIOMES.getValues()).stream().filter(islandsIgnorePredicate).collect(Collectors.toList()))
		{
			double weight = CommonConfig.instance().getIslandBiomeWeight(biome.getRegistryName());
			islandBiomesList.addEntry(biome, weight);
		}	
		
		List<String> bannedOceanBiomes = CommonConfig.instance().getBannedOceanBiomes();
		Predicate<Biome> isOceanPredicate = (biome) -> biome.getCategory() == Category.OCEAN;
		Predicate<Biome> oceanIgnorePredicate = (biome) -> !bannedOceanBiomes.contains(biome.getRegistryName().toString());
		for(Biome biome : new ArrayList<Biome>(ForgeRegistries.BIOMES.getValues()).stream().filter(oceanIgnorePredicate).filter(isOceanPredicate).collect(Collectors.toList()))
		{
			double weight = CommonConfig.instance().getOceanBiomeWeight(biome.getRegistryName());
			oceanBiomesList.addEntry(biome, weight);
		}
	}
	
	public static boolean isOcean(int biomeId)
	{
		for (Biome biome : ForgeRegistries.BIOMES.getValues())
		{
			if (getBiomeId(biome) == biomeId)
				return true;
		}

		return false;
	}

	public static Set<Biome> getBiomeSet()
	{
		Set<Biome> set = new HashSet<>();
		ForgeRegistries.BIOMES.forEach(set::add);
		return set;
	}

	public static int getRandomIslandBiome(INoiseRandom rand)
	{
		if(islandBiomesList.size() == 0)
			return getBiomeId(Biomes.FOREST);
		Biome biome = islandBiomesList.getRandom();
		return getBiomeId(biome);
	}
	
	public static int getRandomOceanBiome(INoiseRandom rand)
	{
		if(oceanBiomesList.size() == 0)
			return getBiomeId(Biomes.DEEP_OCEAN);
		Biome biome = oceanBiomesList.getRandom();
		return getBiomeId(biome);
	}

	public static int getBiomeId(Biome biome)
	{
		return ((ForgeRegistry<Biome>) ForgeRegistries.BIOMES).getID(biome);
	}

	public static Biome getBiome(int biomeId)
	{
		for (Biome biome : ForgeRegistries.BIOMES.getValues())
		{
			if (getBiomeId(biome) == biomeId)
				return biome;
		}
		
		return Biomes.DEEP_OCEAN;
	}
}

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
		List<String> bannedBiomes = CommonConfig.instance().getBannedIslandsBiomes();
		Predicate<Biome> ignorePredicate = (biome) -> !bannedBiomes.contains(biome.getRegistryName().toString());

		List<Biome> list = new ArrayList<Biome>(ForgeRegistries.BIOMES.getValues()).stream().filter(ignorePredicate).collect(Collectors.toList());
		if(list.size() == 0)
			return getBiomeId(Biomes.FOREST);
		Biome biome = list.get(rand.random(list.size()));
		return getBiomeId(biome);
	}
	
	public static int getRandomOceanBiome(INoiseRandom rand)
	{
		List<String> bannedBiomes = CommonConfig.instance().getBannedOceanBiomes();
		Predicate<Biome> ignorePredicate = (biome) -> !bannedBiomes.contains(biome.getRegistryName().toString());
		Predicate<Biome> oceanPredicate = (biome) -> biome.getCategory() == Category.OCEAN;
		
		List<Biome> list = new ArrayList<Biome>(ForgeRegistries.BIOMES.getValues()).stream().filter(ignorePredicate).filter(oceanPredicate).collect(Collectors.toList());
		if(list.size() == 0)
			return getBiomeId(Biomes.DEEP_OCEAN);
		Biome biome = list.get(rand.random(list.size()));
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

package xyz.pixelated.islands.helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import xyz.pixelated.islands.config.CommonConfig;

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

	public static int getRandomBiome(INoiseRandom rand)
	{
		List<String> bannedBiomes = CommonConfig.instance().getBannedBiomes();
		Predicate<Biome> ignorePredicate = (biome) -> !bannedBiomes.contains(biome.getRegistryName().toString());

		List<Biome> list = new ArrayList<Biome>(ForgeRegistries.BIOMES.getValues()).stream().filter(ignorePredicate).collect(Collectors.toList());
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

package xyz.pixelatedw.islands.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import net.minecraft.util.RegistryKey;
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

	public static List<Biome> getBiomeSet()
	{
		List<Biome> list = new ArrayList<>();
		ForgeRegistries.BIOMES.forEach(list::add);
		return list;
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
	
	public static int getBiomeId(RegistryKey<Biome> key)
	{
		Optional<Entry<RegistryKey<Biome>, Biome>> biomeEntry = ((ForgeRegistry<Biome>) ForgeRegistries.BIOMES).getEntries().stream().filter(entry -> entry.getKey() == key).findFirst();
		if(biomeEntry.isPresent())
		{
			Biome biome = biomeEntry.get().getValue();
			return ((ForgeRegistry<Biome>) ForgeRegistries.BIOMES).getID(biome);
		}
		return 0;
	}
	
	@Nullable
	public static Biome getBiomeFromKey(RegistryKey<Biome> key)
	{
		Optional<Entry<RegistryKey<Biome>, Biome>> biomeEntry = ((ForgeRegistry<Biome>) ForgeRegistries.BIOMES).getEntries().stream().filter(entry -> entry.getKey() == key).findFirst();
		if(biomeEntry.isPresent())
		{
			Biome biome = biomeEntry.get().getValue();
			return biome;
		}
		
		return null;
	}

	public static Biome getBiome(int biomeId)
	{
		for (Biome biome : ForgeRegistries.BIOMES.getValues())
		{
			if (getBiomeId(biome) == biomeId)
				return biome;
		}
		
		return getBiomeFromKey(Biomes.DEEP_OCEAN);
	}
}

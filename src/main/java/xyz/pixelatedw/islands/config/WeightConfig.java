package xyz.pixelatedw.islands.config;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;

public class WeightConfig
{
	private static HashMap<String, Number> biomesWeightList;
	
	public static void init()
	{
		biomesWeightList = new HashMap<>();

		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();

		String islandWeightsJson = "";
		File weightConfig = new File("config/islands-weights.json");
		try
		{
			if(!weightConfig.createNewFile())
			{
				islandWeightsJson = new String(Files.readAllBytes(weightConfig.toPath()));
			}
		}
		catch (IOException e)
		{
			LogManager.getLogger().error(e.getMessage());
		}

		if(!islandWeightsJson.isEmpty())
		{
			try
			{
				biomesWeightList = gson.fromJson(islandWeightsJson, HashMap.class);
			}
			catch (Exception e)
			{
				LogManager.getLogger().error(e.getMessage());
			}
		}

		for (Biome biome : ForgeRegistries.BIOMES)
		{
			if(!biomesWeightList.containsKey(biome.getRegistryName().toString()))
			{
				biomesWeightList.put(biome.getRegistryName().toString(), 5);
			}
			else
			{
				int weight = biomesWeightList.get(biome.getRegistryName().toString()).intValue();
				if(!(weight >= 0 && weight <=100))
				{
					weight = 5;
				}
				biomesWeightList.put(biome.getRegistryName().toString(), weight);
			}
		}

		try {
			Files.write(weightConfig.toPath(), gson.toJson(biomesWeightList).getBytes(StandardCharsets.UTF_8));
		} catch (Exception e) {
			LogManager.getLogger().error(e.getMessage());
		}

	}
	
	public static int getIslandBiomeWeight(ResourceLocation key)
	{
		String keyString = key.toString();
		if(biomesWeightList.containsKey(keyString))
			return biomesWeightList.get(keyString).intValue();
		return 5;
	}
	
	public static int getOceanBiomeWeight(ResourceLocation key)
	{
		String keyString = key.toString();
		if(biomesWeightList.containsKey(keyString))
			return biomesWeightList.get(keyString).intValue();
		return 5;
	}
}

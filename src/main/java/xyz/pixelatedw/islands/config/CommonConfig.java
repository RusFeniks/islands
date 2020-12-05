package xyz.pixelatedw.islands.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import xyz.pixelatedw.islands.helpers.WyHelper;

public class CommonConfig
{
	private static CommonConfig instance;

	private IntValue islandRarity;
	private IntValue islandMinSize;
	private IntValue islandMaxSize;
	private ConfigValue<List<? extends String>> bannedBiomes;
	private ConfigValue<List<? extends String>> bannedOceans;
	private BooleanValue survivalIsland;
	private HashMap<ResourceLocation, DoubleValue> islandBiomesWeight;
	private HashMap<ResourceLocation, DoubleValue> oceanBiomesWeight;
	
	private	static final Predicate<Object> ALLOWED_STRING_PREDICATE = new Predicate<Object>()
	{
		@Override
		public boolean test(Object t)
		{
			if (!(t instanceof String))
				return false;

			String str = (String) t;
			return !WyHelper.isNullOrEmpty(str);
		}
	};
	
	public static void init()
	{
		Pair<CommonConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
		ForgeConfigSpec configSpec = pair.getRight();
		CommonConfig.instance = pair.getLeft();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, configSpec);
	}
	
	public static CommonConfig instance()
	{
		return instance;
	}

	public CommonConfig(ForgeConfigSpec.Builder builder)
	{
		builder.push("Islands");
		{
			this.islandRarity = builder.comment("Determines how rare islands will generate \nLower numbers means the islands will be more common!\n10 by default").defineInRange("Rarity", 10, 2, 10000);
			this.islandMinSize = builder.comment("The minimum size an island can be \n2 by default").defineInRange("Minimum Size", 2, 1, 10);
			this.islandMaxSize = builder.comment("The maximum size an island can be \n5 by default").defineInRange("Maximum Size", 5, 1, 10);
			this.survivalIsland = builder.comment("Determines if the spawn island is the only island spawned in the world \nfalse by default").define("Survival Island", false);

			ArrayList defaultBanListIslands = new ArrayList<String>();
			defaultBanListIslands.add(Biomes.THE_END.getLocation().toString());
			defaultBanListIslands.add(Biomes.END_BARRENS.getLocation().toString());
			defaultBanListIslands.add(Biomes.END_HIGHLANDS.getLocation().toString());
			defaultBanListIslands.add(Biomes.END_MIDLANDS.getLocation().toString());
			defaultBanListIslands.add(Biomes.SMALL_END_ISLANDS.getLocation().toString());
			defaultBanListIslands.add(Biomes.THE_END.getLocation().toString());
			defaultBanListIslands.add(Biomes.NETHER_WASTES.getLocation().toString());
			defaultBanListIslands.add(Biomes.SOUL_SAND_VALLEY.getLocation().toString());
			defaultBanListIslands.add(Biomes.CRIMSON_FOREST.getLocation().toString());
			defaultBanListIslands.add(Biomes.WARPED_FOREST.getLocation().toString());
			defaultBanListIslands.add(Biomes.BASALT_DELTAS.getLocation().toString());
			defaultBanListIslands.add(Biomes.THE_VOID.getLocation().toString());
			this.bannedBiomes = builder.comment("List of banned biomes used for island generation, formated as resource keys <mod>:<biome> if <mod> is left out the system will treat them as vanilla biomes\nNote: If all the biomes are removed the Forest biome will be used as a default for all islands!").defineList("Banned Island Biomes", defaultBanListIslands, ALLOWED_STRING_PREDICATE);
			
			ArrayList defaultBanListOceans = new ArrayList<String>();
			this.bannedOceans = builder.comment("List of banned biomes used for ocean generation, formated as resource keys <mod>:<biome> if <mod> is left out the system will treat them as vanilla biomes\nNote 1: While any biome can be added here if the biome is not an ocean it will have no effect on the ocean generation!\nNote 2: If all oceans are removed then the Deep Ocean biome will be used as a default!").defineList("Banned Ocean Biomes", defaultBanListOceans, ALLOWED_STRING_PREDICATE);
		
			builder.comment("If a biome is not included here it has a default weight of 1!").push("Island Biomes Weight");
			{
				this.islandBiomesWeight = new HashMap<ResourceLocation, DoubleValue>();
				this.islandBiomesWeight.put(Biomes.FOREST.getLocation(), builder.defineInRange(Biomes.FOREST.getLocation().toString(), 1, 0.1, 100));
			}
			builder.pop();
			
			builder.comment("If a biome is not included here it has a default weight of 1!").push("Ocean Biomes Weight");
			{
				this.oceanBiomesWeight = new HashMap<ResourceLocation, DoubleValue>();
				this.oceanBiomesWeight.put(Biomes.OCEAN.getLocation(), builder.defineInRange(Biomes.OCEAN.getLocation().toString(), 1, 0.1, 100));
			}
			builder.pop();
		}
	}
	
	public double getOceanBiomeWeight(ResourceLocation key)
	{
		if(this.oceanBiomesWeight.containsKey(key))
			this.oceanBiomesWeight.get(key).get();
		return 1;	
	}
	
	public double getIslandBiomeWeight(ResourceLocation key)
	{
		if(this.islandBiomesWeight.containsKey(key))
			this.islandBiomesWeight.get(key).get();
		return 1;	
	}
	
	public List<String> getBannedOceanBiomes()
	{
		return (List<String>) this.bannedOceans.get();
	}
	
	public boolean isSurvivalIsland()
	{
		return this.survivalIsland.get();
	}
	
	public List<String> getBannedIslandsBiomes()
	{
		return (List<String>) this.bannedBiomes.get();
	}
	
	public int getIslandMaxSize()
	{
		return this.islandMaxSize.get();
	}
	
	public int getIslandMinSize()
	{
		return this.islandMinSize.get();
	}
	
	public int getIslandRarity()
	{
		return this.islandRarity.get();
	}
}

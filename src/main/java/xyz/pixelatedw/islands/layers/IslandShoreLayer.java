package xyz.pixelatedw.islands.layers;

import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;
import xyz.pixelatedw.islands.config.CommonConfig;
import xyz.pixelatedw.islands.helpers.IslandsHelper;

public enum IslandShoreLayer implements ICastleTransformer
{
	INSTANCE;

	private static final int BEACH = IslandsHelper.getBiomeId(Biomes.BEACH);
	private static final int SNOWY_BEACH = IslandsHelper.getBiomeId(Biomes.SNOWY_BEACH);
	private static final int DESERT = IslandsHelper.getBiomeId(Biomes.DESERT);
	private static final int MOUNTAINS = IslandsHelper.getBiomeId(Biomes.MOUNTAINS);
	private static final int WOODED_MOUNTAINS = IslandsHelper.getBiomeId(Biomes.WOODED_MOUNTAINS);
	private static final int FOREST = IslandsHelper.getBiomeId(Biomes.FOREST);
	private static final int JUNGLE = IslandsHelper.getBiomeId(Biomes.JUNGLE);
	private static final int JUNGLE_EDGE = IslandsHelper.getBiomeId(Biomes.JUNGLE_EDGE);
	private static final int JUNGLE_HILLS = IslandsHelper.getBiomeId(Biomes.JUNGLE_HILLS);
	private static final int BADLANDS = IslandsHelper.getBiomeId(Biomes.BADLANDS);
	private static final int WOODED_BADLANDS_PLATEAU = IslandsHelper.getBiomeId(Biomes.WOODED_BADLANDS_PLATEAU);
	private static final int BADLANDS_PLATEAU = IslandsHelper.getBiomeId(Biomes.BADLANDS_PLATEAU);
	private static final int ERODED_BADLANDS = IslandsHelper.getBiomeId(Biomes.ERODED_BADLANDS);
	private static final int MODIFIED_WOODED_BADLANDS_PLATEAU = IslandsHelper.getBiomeId(Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU);
	private static final int MODIFIED_BADLANDS_PLATEAU = IslandsHelper.getBiomeId(Biomes.MODIFIED_BADLANDS_PLATEAU);
	private static final int MUSHROOM_FIELDS = IslandsHelper.getBiomeId(Biomes.MUSHROOM_FIELDS);
	private static final int MUSHROOM_FIELD_SHORE = IslandsHelper.getBiomeId(Biomes.MUSHROOM_FIELD_SHORE);
	private static final int RIVER = IslandsHelper.getBiomeId(Biomes.RIVER);
	private static final int MOUNTAIN_EDGE = IslandsHelper.getBiomeId(Biomes.MOUNTAIN_EDGE);
	private static final int STONE_SHORE = IslandsHelper.getBiomeId(Biomes.STONE_SHORE);
	private static final int SWAMP = IslandsHelper.getBiomeId(Biomes.SWAMP);
	private static final int TAIGA = IslandsHelper.getBiomeId(Biomes.TAIGA);

	private static final int WARM_OCEAN = IslandsHelper.getBiomeId(Biomes.WARM_OCEAN);
	private static final int LUKEWARM_OCEAN = IslandsHelper.getBiomeId(Biomes.LUKEWARM_OCEAN);
	private static final int OCEAN = IslandsHelper.getBiomeId(Biomes.OCEAN);
	private static final int COLD_OCEAN = IslandsHelper.getBiomeId(Biomes.COLD_OCEAN);
	private static final int FROZEN_OCEAN = IslandsHelper.getBiomeId(Biomes.FROZEN_OCEAN);
	private static final int DEEP_WARM_OCEAN = IslandsHelper.getBiomeId(Biomes.DEEP_WARM_OCEAN);
	private static final int DEEP_LUKEWARM_OCEAN = IslandsHelper.getBiomeId(Biomes.DEEP_LUKEWARM_OCEAN);
	private static final int DEEP_OCEAN = IslandsHelper.getBiomeId(Biomes.DEEP_OCEAN);
	private static final int DEEP_COLD_OCEAN = IslandsHelper.getBiomeId(Biomes.DEEP_COLD_OCEAN);
	private static final int DEEP_FROZEN_OCEAN = IslandsHelper.getBiomeId(Biomes.DEEP_FROZEN_OCEAN);

	public int apply(INoiseRandom context, int north, int west, int south, int east, int center)
	{
		Biome biome = IslandsHelper.getBiome(center);
		if (center == MUSHROOM_FIELDS)
		{
			if (isShallowOcean(north) || isShallowOcean(west) || isShallowOcean(south) || isShallowOcean(east))
			{
				if(!isShoreBanned(Biomes.MUSHROOM_FIELD_SHORE))
					return MUSHROOM_FIELD_SHORE;
				else
					return center;
			}
		}
		else if (biome != null && biome.getCategory() == Biome.Category.JUNGLE)
		{
			if (!isJungleCompatible(north) || !isJungleCompatible(west) || !isJungleCompatible(south) || !isJungleCompatible(east))
			{
				if(!isShoreBanned(Biomes.JUNGLE_EDGE))
					return JUNGLE_EDGE;
				else
					return center;
			}

			if (isOcean(north) || isOcean(west) || isOcean(south) || isOcean(east))
			{
				if(!isShoreBanned(Biomes.BEACH))
					return BEACH;
				else
					return center;
			}
		}
		else if (center != MOUNTAINS && center != WOODED_MOUNTAINS && center != MOUNTAIN_EDGE)
		{
			if (biome != null && biome.getPrecipitation() == Biome.RainType.SNOW)
			{
				if (!isOcean(center) && (isOcean(north) || isOcean(west) || isOcean(south) || isOcean(east)))
				{
					if(!isShoreBanned(Biomes.SNOWY_BEACH))
						return SNOWY_BEACH;
					else
						return center;
				}
			}
			else if (center != BADLANDS && center != WOODED_BADLANDS_PLATEAU)
			{
				if (!isOcean(center) && center != RIVER && center != SWAMP && (isOcean(north) || isOcean(west) || isOcean(south) || isOcean(east)))
				{
					if(!isShoreBanned(Biomes.BEACH))
						return BEACH;
					else
						return center;
				}
			}
			else if (!isOcean(north) && !isOcean(west) && !isOcean(south) && !isOcean(east) && (!this.isMesa(north) || !this.isMesa(west) || !this.isMesa(south) || !this.isMesa(east)))
			{
				if(!isShoreBanned(Biomes.DESERT))
					return DESERT;
				else
					return center;
			}
		}
		else if (!isOcean(center) && (isOcean(north) || isOcean(west) || isOcean(south) || isOcean(east)))
		{
			if(!isShoreBanned(Biomes.STONE_SHORE))
				return STONE_SHORE;
			else
				return center;
		}

		return center;
	}

	private boolean isJungleCompatible(int biomeId)
	{
		if (IslandsHelper.getBiome(biomeId) != null && IslandsHelper.getBiome(biomeId).getCategory() == Biome.Category.JUNGLE)
		{
			return true;
		}
		else
		{
			return biomeId == JUNGLE_EDGE || biomeId == JUNGLE || biomeId == JUNGLE_HILLS || biomeId == FOREST || biomeId == TAIGA || isOcean(biomeId);
		}
	}

	private boolean isMesa(int biomeId)
	{
		return biomeId == BADLANDS || biomeId == WOODED_BADLANDS_PLATEAU || biomeId == BADLANDS_PLATEAU || biomeId == ERODED_BADLANDS || biomeId == MODIFIED_WOODED_BADLANDS_PLATEAU || biomeId == MODIFIED_BADLANDS_PLATEAU;
	}

	private boolean isOcean(int biomeIn)
	{
		return biomeIn == WARM_OCEAN || biomeIn == LUKEWARM_OCEAN || biomeIn == OCEAN || biomeIn == COLD_OCEAN || biomeIn == FROZEN_OCEAN || biomeIn == DEEP_WARM_OCEAN || biomeIn == DEEP_LUKEWARM_OCEAN || biomeIn == DEEP_OCEAN || biomeIn == DEEP_COLD_OCEAN || biomeIn == DEEP_FROZEN_OCEAN;
	}

	private boolean isShallowOcean(int biomeIn)
	{
		return biomeIn == WARM_OCEAN || biomeIn == LUKEWARM_OCEAN || biomeIn == OCEAN || biomeIn == COLD_OCEAN || biomeIn == FROZEN_OCEAN;
	}
	
	private boolean isShoreBanned(RegistryKey<Biome> registryKey)
	{
		return CommonConfig.instance().getBannedIslandsBiomes().contains(IslandsHelper.getBiomeFromKey(registryKey).getRegistryName().toString());
	}
}
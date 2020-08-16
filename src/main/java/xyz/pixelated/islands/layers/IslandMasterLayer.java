package xyz.pixelated.islands.layers;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import xyz.pixelated.islands.config.CommonConfig;
import xyz.pixelated.islands.helpers.IslandsHelper;

public enum IslandMasterLayer implements IAreaTransformer0
{
	INSTANCE;

	public int islandRarity;

	IslandMasterLayer()
	{
		this.islandRarity = CommonConfig.instance().getIslandRarity();
	}

	@Override
	public int apply(INoiseRandom rand, int genX, int genZ)
	{
		if (genX == 0 && genZ == 0)
			return ((ForgeRegistry<Biome>) ForgeRegistries.BIOMES).getID(Biomes.FOREST);
		if (CommonConfig.instance().isSurvivalIsland())
		      return IslandsHelper.getRandomOceanBiome(rand); 
		return (rand.random(this.islandRarity) == 1) ? IslandsHelper.getRandomIslandBiome(rand) : IslandsHelper.getRandomOceanBiome(rand);
	}
}

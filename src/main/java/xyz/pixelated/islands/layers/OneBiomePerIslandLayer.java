package xyz.pixelated.islands.layers;

import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;
import xyz.pixelated.islands.helpers.IslandsHelper;

public enum OneBiomePerIslandLayer implements ICastleTransformer
{
	INSTANCE;

	@Override
	public int apply(INoiseRandom rand, int north, int west, int south, int east, int centre)
	{
		if (!IslandsHelper.isOcean(centre) && (!IslandsHelper.isOcean(north) || !IslandsHelper.isOcean(south) || !IslandsHelper.isOcean(east) || !IslandsHelper.isOcean(west)))
			return IslandsHelper.getBiomeId(Biomes.DEEP_OCEAN);
		return centre;
	}
}

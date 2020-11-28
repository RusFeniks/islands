package xyz.pixelatedw.islands.layers;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;
import xyz.pixelatedw.islands.helpers.IslandsHelper;

public enum OneBiomePerIslandLayer implements ICastleTransformer
{
	INSTANCE;

	@Override
	public int apply(INoiseRandom rand, int north, int west, int south, int east, int center)
	{
		if (!IslandsHelper.isOcean(center) && (!IslandsHelper.isOcean(north) || !IslandsHelper.isOcean(south) || !IslandsHelper.isOcean(east) || !IslandsHelper.isOcean(west)))
			return IslandsHelper.getRandomOceanBiome(rand);
		return center;
	}
}

package xyz.pixelatedw.islands.layers;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;
import xyz.pixelatedw.islands.helpers.IslandsHelper;

public enum OneBiomePerIslandLayer implements ICastleTransformer
{
	INSTANCE;

	@Override
	public int apply(INoiseRandom rand, int north, int west, int south, int east, int centre)
	{
		boolean isNorth = IslandsHelper.isOcean(north);
		boolean isSouth = IslandsHelper.isOcean(south);
		boolean isEast = IslandsHelper.isOcean(east);
		boolean isWest = IslandsHelper.isOcean(west);
		boolean isNorthWest = IslandsHelper.isOcean(north) && IslandsHelper.isOcean(west);
		boolean isNorthEast = IslandsHelper.isOcean(north) && IslandsHelper.isOcean(east);
		boolean isSouthWest = IslandsHelper.isOcean(south) && IslandsHelper.isOcean(west);
		boolean isSouthEast = IslandsHelper.isOcean(south) && IslandsHelper.isOcean(east);

		if (!IslandsHelper.isOcean(centre) && (!isNorth || !isSouth || !isEast || !isWest || !isNorthWest || !isNorthEast || !isSouthWest || !isSouthEast))
			return IslandsHelper.getRandomOceanBiome(rand);
		
		return centre;
	}
}

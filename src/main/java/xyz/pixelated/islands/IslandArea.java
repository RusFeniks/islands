package xyz.pixelated.islands;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import xyz.pixelated.islands.helpers.IslandsHelper;

public class IslandArea
{
	private final LazyArea lazyArea;

	public IslandArea(IAreaFactory<LazyArea> lazyAreaFactoryIn)
	{
		this.lazyArea = lazyAreaFactoryIn.make();
	}

	public Biome[] generateBiomes(int startX, int startZ, int xSize, int zSize)
	{
		Biome[] abiome = new Biome[xSize * zSize];
		for (int i = 0; i < zSize; i++)
		{
			for (int j = 0; j < xSize; j++)
			{
				int k = this.lazyArea.getValue(startX + j, startZ + i);
				Biome biome = IslandsHelper.getBiome(k);
				abiome[j + i * xSize] = biome;
			}
		}
		return abiome;
	}

	public Biome getBiome(int x, int z)
	{
		return IslandsHelper.getBiome(this.lazyArea.getValue(x, z));
	}
}

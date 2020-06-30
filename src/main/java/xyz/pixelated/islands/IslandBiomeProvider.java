package xyz.pixelated.islands;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.layer.Layer;
import xyz.pixelated.islands.helpers.IslandsHelper;

public class IslandBiomeProvider extends BiomeProvider
{	
	private Layer biomeLayer;
	
	public IslandBiomeProvider(long seed)
	{
		super(IslandsHelper.getBiomeSet());
		this.biomeLayer = IslandLayerProvider.build(seed);
	}

	@Override
	public Biome getNoiseBiome(int x, int y, int z)
	{
		return this.biomeLayer.func_215738_a(x, z);
	}
}
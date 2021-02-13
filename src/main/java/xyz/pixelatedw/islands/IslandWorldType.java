package xyz.pixelatedw.islands;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraftforge.common.world.ForgeWorldType;

public class IslandWorldType extends ForgeWorldType
{
	public IslandWorldType()
	{
		super(new Factory());
		this.setRegistryName(new ResourceLocation("islands"));
	}

	private static class Factory implements IBasicChunkGeneratorFactory
	{
		@Override
		public ChunkGenerator createChunkGenerator(Registry<Biome> biomeRegistry, Registry<DimensionSettings> dimensionSettingsRegistry, long seed)
		{
			return new NoiseChunkGenerator(new IslandBiomeProvider(seed, biomeRegistry), seed, () ->
			{
				return dimensionSettingsRegistry.getOrThrow(DimensionSettings.field_242734_c);
			});
		}
	}
}
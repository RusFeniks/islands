package xyz.pixelatedw.islands;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.layer.Layer;
import xyz.pixelatedw.islands.helpers.IslandsHelper;

public class IslandBiomeProvider extends BiomeProvider
{
	private Layer biomeLayer;
	private Registry<Biome> lookupRegistry;
	public static final Codec<IslandBiomeProvider> CODEC = RecordCodecBuilder.create((builder) ->
	{
		return builder.group(Codec.LONG.fieldOf("seed").stable().forGetter((provider) ->
		{
			return provider.seed;
		}), RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter((overworldProvider) ->
		{
			return overworldProvider.lookupRegistry;
		})).apply(builder, builder.stable(IslandBiomeProvider::new));
	});
	private final long seed;

	public IslandBiomeProvider(long seed, Registry<Biome> lookupRegistry)
	{
		super(IslandsHelper.getBiomeSet());
		this.seed = seed;
		this.biomeLayer = IslandLayerProvider.build(seed);
		this.lookupRegistry = lookupRegistry;
	}

	@Override
	public Biome getNoiseBiome(int x, int y, int z)
	{
		return this.biomeLayer.func_242936_a(this.lookupRegistry, x, z);
	}

	@Override
	protected Codec<? extends BiomeProvider> getBiomeProviderCodec()
	{
		return CODEC;
	}

	@Override
	public BiomeProvider getBiomeProvider(long seed)
	{
		return new IslandBiomeProvider(seed, this.lookupRegistry);
	}
}
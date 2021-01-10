package xyz.pixelatedw.islands;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.registries.ForgeRegistries;

public class IslandBiomeProvider extends BiomeProvider
{	
	private final IslandArea genBiomes;
	private final IslandArea biomeFactoryLayer;

	public IslandBiomeProvider(OverworldBiomeProviderSettings settingsProvider)
	{
		WorldInfo worldinfo = settingsProvider.getWorldInfo();
		OverworldGenSettings overworldgensettings = settingsProvider.getGeneratorSettings();
		IslandArea[] area = IslandLayerProvider.build(worldinfo.getSeed(), worldinfo.getGenerator(), overworldgensettings);
		this.genBiomes = area[0];
		this.biomeFactoryLayer = area[1];
	}

	@Override
	public Biome getBiome(int x, int y)
	{
		return this.biomeFactoryLayer.getBiome(x, y);
	}

	@Override
	public Biome getBiomeAtFactorFour(int x, int z)
	{
		return this.genBiomes.getBiome(x, z);
	}

	@Override
	public Biome[] getBiomes(int x, int z, int width, int length, boolean cacheFlag)
	{
		return this.biomeFactoryLayer.generateBiomes(x, z, width, length);
	}

	@Override
	public Set<Biome> getBiomesInSquare(int centerX, int centerZ, int sideLength)
	{
		int i = centerX - sideLength >> 2;
		int j = centerZ - sideLength >> 2;
		int k = centerX + sideLength >> 2;
		int l = centerZ + sideLength >> 2;
		int i1 = k - i + 1;
		int j1 = l - j + 1;
		Set<Biome> set = Sets.newHashSet();
		Collections.addAll(set, this.genBiomes.generateBiomes(i, j, i1, j1));
		return set;
	}

	@Override
	@Nullable
	public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random)
	{
		int i = x - range >> 2;
		int j = z - range >> 2;
		int k = x + range >> 2;
		int l = z + range >> 2;
		int i1 = k - i + 1;
		int j1 = l - j + 1;
		Biome[] abiome = this.genBiomes.generateBiomes(i, j, i1, j1);
		BlockPos blockpos = null;
		int k1 = 0;
		for (int l1 = 0; l1 < i1 * j1; l1++)
		{
			int i2 = i + l1 % i1 << 2;
			int j2 = j + l1 / i1 << 2;
			if (biomes.contains(abiome[l1]))
			{
				if (blockpos == null || random.nextInt(k1 + 1) == 0)
					blockpos = new BlockPos(i2, 0, j2);
				k1++;
			}
		}
		return blockpos;
	}

	@Override
	public boolean hasStructure(Structure<?> structureIn)
	{
		return this.hasStructureCache.computeIfAbsent(structureIn, structure ->
		{
			for (Biome biome : ForgeRegistries.BIOMES.getValues())
			{
				if (biome.hasStructure(structure))
					return true;
			}
			return false;
		});
	}

	@Override
	public Set<BlockState> getSurfaceBlocks()
	{
		if (this.topBlocksCache.isEmpty())
			for (Biome biome : ForgeRegistries.BIOMES.getValues())
				this.topBlocksCache.add(biome.getSurfaceBuilderConfig().getTop());
		return this.topBlocksCache;
	}
}
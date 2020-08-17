package xyz.pixelatedw.islands;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.OverworldChunkGenerator;
import net.minecraft.world.gen.OverworldGenSettings;

public class IslandWorldType extends WorldType
{
	public IslandWorldType()
	{
		super("islands");
	}

	@Override
	public ChunkGenerator<?> createChunkGenerator(World world)
	{
		if (world.dimension.getType() == DimensionType.OVERWORLD)
			return new OverworldChunkGenerator(world, new IslandBiomeProvider((new OverworldBiomeProviderSettings()).setWorldInfo(world.getWorldInfo())), new OverworldGenSettings());

		return super.createChunkGenerator(world);
	}
}

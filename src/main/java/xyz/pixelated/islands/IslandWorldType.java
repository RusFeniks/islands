package xyz.pixelated.islands;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.ChunkGeneratorType;
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
		return ChunkGeneratorType.SURFACE.create(world, new IslandBiomeProvider(world.getSeed()), new OverworldGenSettings());
	}
}

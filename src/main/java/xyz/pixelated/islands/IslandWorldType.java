package xyz.pixelated.islands;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.dimension.DimensionType;
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
		if(world.getDimension().getType() == DimensionType.OVERWORLD)
		    return ChunkGeneratorType.SURFACE.create(world, new IslandBiomeProvider(world.getSeed()), new OverworldGenSettings());

		return super.createChunkGenerator(world);
	}
}

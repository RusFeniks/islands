package xyz.pixelated.islands;

import java.util.function.LongFunction;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.ShoreLayer;
import net.minecraft.world.gen.layer.ZoomLayer;
import xyz.pixelated.islands.config.CommonConfig;
import xyz.pixelated.islands.helpers.WyHelper;
import xyz.pixelated.islands.layers.IslandMasterLayer;
import xyz.pixelated.islands.layers.OneBiomePerIslandLayer;

public class IslandLayerProvider
{
	private static <A extends IArea, R extends IExtendedNoiseRandom<A>> IAreaFactory<A> setup(LongFunction<R> randomProvider) 
	{
		IAreaFactory<A> islandFactory = IslandMasterLayer.INSTANCE.apply((IExtendedNoiseRandom) randomProvider.apply(1000L));
		islandFactory = OneBiomePerIslandLayer.INSTANCE.apply((IExtendedNoiseRandom) randomProvider.apply(9L), islandFactory);
		int min = CommonConfig.instance().getIslandMinSize();
		int max = CommonConfig.instance().getIslandMaxSize();
		int size = (int) WyHelper.randomWithRange(min, max);
		for (int islandSize = 0; islandSize <= size; islandSize++)
			islandFactory = ZoomLayer.NORMAL.apply((IExtendedNoiseRandom) randomProvider.apply(1000L + islandSize), islandFactory);
		islandFactory = ShoreLayer.INSTANCE.apply((IExtendedNoiseRandom) randomProvider.apply(10L), islandFactory);
		islandFactory = ZoomLayer.FUZZY.apply((IExtendedNoiseRandom) randomProvider.apply(1000L), islandFactory);
		islandFactory = ZoomLayer.NORMAL.apply((IExtendedNoiseRandom) randomProvider.apply(1000L), islandFactory);
		return islandFactory;
	}

	public static Layer build(long seed)
	{
		return new Layer(setup(salt -> new LazyAreaLayerContext(25, seed, salt)));
	}
}

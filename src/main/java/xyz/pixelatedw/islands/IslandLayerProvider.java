package xyz.pixelatedw.islands;

import java.util.function.LongFunction;

import com.google.common.collect.ImmutableList;

import net.minecraft.world.WorldType;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.ShoreLayer;
import net.minecraft.world.gen.layer.VoroniZoomLayer;
import net.minecraft.world.gen.layer.ZoomLayer;
import xyz.pixelatedw.islands.config.CommonConfig;
import xyz.pixelatedw.islands.helpers.WyHelper;
import xyz.pixelatedw.islands.layers.IslandMasterLayer;
import xyz.pixelatedw.islands.layers.OneBiomePerIslandLayer;

public class IslandLayerProvider
{
	public static <T extends IArea, C extends IExtendedNoiseRandom<T>> ImmutableList<IAreaFactory<T>> setup(WorldType worldType, OverworldGenSettings settings, LongFunction<C> randomProvider)
	{
		IAreaFactory<T> islandFactory = IslandMasterLayer.INSTANCE.apply((IExtendedNoiseRandom) randomProvider.apply(1000L));
		islandFactory = OneBiomePerIslandLayer.INSTANCE.apply((IExtendedNoiseRandom) randomProvider.apply(9L), islandFactory);
		int min = CommonConfig.instance().getIslandMinSize();
		int max = CommonConfig.instance().getIslandMaxSize();
		int size = (int) WyHelper.randomWithRange(min, max);
		for (int islandSize = 0; islandSize <= size; islandSize++)
			islandFactory = ZoomLayer.NORMAL.apply((IExtendedNoiseRandom) randomProvider.apply(1000L), islandFactory);
		islandFactory = ShoreLayer.INSTANCE.apply((IExtendedNoiseRandom) randomProvider.apply(10L), islandFactory);
		islandFactory = ZoomLayer.FUZZY.apply((IExtendedNoiseRandom) randomProvider.apply(1000L), islandFactory);
		islandFactory = ZoomLayer.NORMAL.apply((IExtendedNoiseRandom) randomProvider.apply(1000L), islandFactory);
		IAreaFactory<T> iareafactory5 = VoroniZoomLayer.INSTANCE.apply((IExtendedNoiseRandom) randomProvider.apply(1000L), islandFactory);
		return ImmutableList.of(islandFactory, iareafactory5);
	}

	public static IslandArea[] build(long seed, WorldType type, OverworldGenSettings settings)
	{
		ImmutableList<IAreaFactory<LazyArea>> immutablelist = setup(type, settings, (modifier) -> new LazyAreaLayerContext(25, seed, modifier));
		IslandArea layer = new IslandArea(immutablelist.get(0));
		IslandArea layer1 = new IslandArea(immutablelist.get(1));
		return new IslandArea[] { layer, layer1 };
	}
}

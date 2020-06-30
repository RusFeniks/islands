package xyz.pixelated.islands;

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
import xyz.pixelated.islands.config.CommonConfig;
import xyz.pixelated.islands.helpers.WyHelper;
import xyz.pixelated.islands.layers.IslandMasterLayer;
import xyz.pixelated.islands.layers.OneBiomePerIslandLayer;

public class IslandLayerProvider
{
	public static <T extends IArea, C extends IExtendedNoiseRandom<T>> ImmutableList<IAreaFactory<T>> setup(WorldType worldTypeIn, OverworldGenSettings settings, LongFunction<C> randomProvider)
	{
		IAreaFactory<T> islandFactory = IslandMasterLayer.INSTANCE.apply((IExtendedNoiseRandom) randomProvider.apply(1000L));
		islandFactory = OneBiomePerIslandLayer.INSTANCE.apply((IExtendedNoiseRandom) randomProvider.apply(9L), islandFactory);
		int min = CommonConfig.instance().getIslandMinSize();
		int max = CommonConfig.instance().getIslandMaxSize();
		int size = (int) WyHelper.randomWithRange(min, max);
		for (int islandSize = 0; islandSize <= size; islandSize++)
			islandFactory = ZoomLayer.NORMAL.apply((IExtendedNoiseRandom) randomProvider.apply(1000L + islandSize), islandFactory);
		islandFactory = ShoreLayer.INSTANCE.apply((IExtendedNoiseRandom) randomProvider.apply(10L), islandFactory);
		islandFactory = ZoomLayer.FUZZY.apply((IExtendedNoiseRandom) randomProvider.apply(1000L), islandFactory);
		islandFactory = ZoomLayer.NORMAL.apply((IExtendedNoiseRandom) randomProvider.apply(1000L), islandFactory);
		IAreaFactory<T> iareafactory5 = VoroniZoomLayer.INSTANCE.apply((IExtendedNoiseRandom) randomProvider.apply(1000L), islandFactory);
		return ImmutableList.of(islandFactory, iareafactory5);
	}

	public static IslandArea[] build(long seed, WorldType typeIn, OverworldGenSettings settings)
	{
		ImmutableList<IAreaFactory<LazyArea>> immutablelist = setup(typeIn, settings, p_215737_2_ -> new LazyAreaLayerContext(25, seed, p_215737_2_));
		IslandArea layer = new IslandArea(immutablelist.get(0));
		IslandArea layer1 = new IslandArea(immutablelist.get(1));
		return new IslandArea[] { layer, layer1 };
	}
}

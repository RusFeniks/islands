package xyz.pixelatedw.islands;

import net.minecraft.world.WorldType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.pixelatedw.islands.config.CommonConfig;
import xyz.pixelatedw.islands.config.IslandsWeightConfig;
import xyz.pixelatedw.islands.config.OceansWeightConfig;
import xyz.pixelatedw.islands.helpers.IslandsHelper;
import xyz.pixelatedw.islands.init.ModValues;

@Mod(ModValues.PROJECT_ID)
public class ModMain
{
	public static final WorldType ISLANDS_TYPE = new IslandWorldType();

	public ModMain()
	{
		CommonConfig.init();
		IslandsWeightConfig.init();
		OceansWeightConfig.init();

		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::onLoadComplete);
	}

	private void onLoadComplete(FMLLoadCompleteEvent event)
	{
		new IslandsHelper();
	}
}

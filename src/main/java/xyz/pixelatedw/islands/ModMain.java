package xyz.pixelatedw.islands;

import net.minecraft.server.dedicated.ServerProperties;
import net.minecraft.world.WorldType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.pixelatedw.islands.config.CommonConfig;
import xyz.pixelatedw.islands.config.WeightConfig;
import xyz.pixelatedw.islands.helpers.IslandsHelper;
import xyz.pixelatedw.islands.init.ModValues;

@Mod(ModValues.PROJECT_ID)
@Mod.EventBusSubscriber(modid = ModValues.PROJECT_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModMain
{
	public static final WorldType ISLANDS_TYPE = new IslandWorldType();

	public ModMain()
	{
		CommonConfig.init();
		WeightConfig.init();
		
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::onLoadComplete);
	}
	
	private void onLoadComplete(FMLLoadCompleteEvent event)
	{
		new IslandsHelper();
	}
	
	@SubscribeEvent
	public static void dedicatedServerSetup(FMLDedicatedServerSetupEvent event)
	{
		ServerProperties serverProperties = event.getServerSupplier().get().getServerProperties();
		if (serverProperties != null)
		{
			String entryValue = serverProperties.serverProperties.getProperty("use-modded-worldtype");
			if (entryValue != null && entryValue.equals("islands"))
				serverProperties.worldType = ISLANDS_TYPE;
		}
	}
}

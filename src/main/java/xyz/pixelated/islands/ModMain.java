package xyz.pixelated.islands;

import net.minecraft.server.dedicated.ServerProperties;
import net.minecraft.world.WorldType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.pixelated.islands.config.CommonConfig;
import xyz.pixelated.islands.init.ModValues;

@Mod(ModValues.PROJECT_ID)
public class ModMain
{
	public static final WorldType ISLANDS_TYPE = new IslandWorldType();

	public ModMain()
	{
		CommonConfig.init();
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::dedicatedServerSetup);
	}
	
	public void dedicatedServerSetup(FMLDedicatedServerSetupEvent event)
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

package xyz.pixelated.islands.setup;

import net.minecraft.world.WorldType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import xyz.pixelated.islands.IslandWorldType;
import xyz.pixelated.islands.init.ModValues;

@Mod.EventBusSubscriber(modid = ModValues.PROJECT_ID)
public class DedicatedServerSetup
{
	private static final WorldType ISLAND_TYPE = new IslandWorldType();
	
	public static void dedicatedServerSetup(FMLDedicatedServerSetupEvent event)
	{
		WorldType.WORLD_TYPES[10] = ISLAND_TYPE;
	}
}

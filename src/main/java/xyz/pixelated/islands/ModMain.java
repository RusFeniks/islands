package xyz.pixelated.islands;

import net.minecraft.world.WorldType;
import net.minecraftforge.fml.common.Mod;
import xyz.pixelated.islands.config.CommonConfig;
import xyz.pixelated.islands.init.ModValues;

@Mod(ModValues.PROJECT_ID)
public class ModMain
{
	public static final WorldType ISLANDS_TYPE = new IslandWorldType();

	public ModMain()
	{
		CommonConfig.init();	
	}
}

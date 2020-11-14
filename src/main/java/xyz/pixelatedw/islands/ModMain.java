package xyz.pixelatedw.islands;

import net.minecraftforge.fml.common.Mod;
import xyz.pixelatedw.islands.config.CommonConfig;
import xyz.pixelatedw.islands.init.ModValues;

@Mod(ModValues.PROJECT_ID)
public class ModMain
{
	// public static final WorldType ISLANDS_TYPE = new IslandWorldType();

	public ModMain()
	{
		CommonConfig.init();
	}
}

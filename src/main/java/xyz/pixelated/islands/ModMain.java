package xyz.pixelated.islands;

import net.minecraftforge.fml.common.Mod;
import xyz.pixelated.islands.config.CommonConfig;
import xyz.pixelated.islands.init.ModValues;

@Mod(ModValues.PROJECT_ID)
public class ModMain
{
	public ModMain()
	{
		CommonConfig.init();
	}
}

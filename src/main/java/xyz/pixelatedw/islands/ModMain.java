package xyz.pixelatedw.islands;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.pixelatedw.islands.config.CommonConfig;
import xyz.pixelatedw.islands.config.WeightConfig;
import xyz.pixelatedw.islands.helpers.IslandsHelper;
import xyz.pixelatedw.islands.init.ModValues;

@Mod(ModValues.PROJECT_ID)
public class ModMain
{
	public static final ForgeWorldType ISLANDS_TYPE = new IslandWorldType();

	public ModMain()
	{
		CommonConfig.init();
		WeightConfig.init();

		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ForgeRegistries.WORLD_TYPES.register(ISLANDS_TYPE);

		modEventBus.addListener(this::onLoadComplete);

		Registry.register(Registry.BIOME_PROVIDER_CODEC, new ResourceLocation(ModValues.PROJECT_ID, "overworld"), IslandBiomeProvider.CODEC);
	}

	private void onLoadComplete(FMLLoadCompleteEvent event)
	{
		new IslandsHelper();
	}
}

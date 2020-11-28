package xyz.pixelatedw.islands;

import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.pixelatedw.islands.config.CommonConfig;
import xyz.pixelatedw.islands.init.ModValues;

@Mod(ModValues.PROJECT_ID)
public class ModMain
{
	public static final DeferredRegister<ForgeWorldType> WORLD_TYPES = DeferredRegister.create(ForgeRegistries.WORLD_TYPES, ModValues.PROJECT_ID);
	
	public static final ForgeWorldType ISLANDS_TYPE = new IslandWorldType();

	public ModMain()
	{		
		CommonConfig.init();
		
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		WORLD_TYPES.register("islands", () -> ISLANDS_TYPE);
		WORLD_TYPES.register(modEventBus);
		
		//FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerWorldTypeScreenFactories);
	}

	/*
	private void registerWorldTypeScreenFactories(FMLClientSetupEvent event)
    {
        ForgeWorldTypeScreens.registerFactory(ISLANDS_TYPE, (returnTo, dimensionGeneratorSettings) -> new Screen(ISLANDS_TYPE.getDisplayName())
        {
            @Override
            protected void init()
            {
                super.init();

                addButton(new Button(0, 0, 120, 20, new StringTextComponent("close"), btn -> {
                    Minecraft.getInstance().displayGuiScreen(returnTo);
                }));
            }
        });
    }
    */
}

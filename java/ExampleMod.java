import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("examplemod")
public class ExampleMod {
    public ExampleMod() {
        ExampleModItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ExampleModItemRegistry.BLOCK.register(FMLJavaModLoadingContext.get().getModEventBus());
        TileEntityTypeRegistry.TILE_ENTITY_TYPE_DEFERRED_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}

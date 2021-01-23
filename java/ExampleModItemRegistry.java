import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExampleModItemRegistry {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, "examplemod");
    public static RegistryObject<Item> obsidianIngot = ITEMS.register("bosidian_examplemod",() -> {
        return new ObsidianIngot();
    });
}

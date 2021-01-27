import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExampleModItemRegistry {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, "examplemod");
    public static RegistryObject<Item> obsidianIngot = ITEMS.register("obsidian_ingot",() -> {
        return new ObsidianIngot();
    });
    public static RegistryObject<Item> obsidianApple = ITEMS.register("obsidian_apple",() -> {
        return new ObsidianApple();
    });
    public static RegistryObject<Item> obsidianSword = ITEMS.register("obsidian_sword",() -> {
        return new ObsidianSword();
    });
}

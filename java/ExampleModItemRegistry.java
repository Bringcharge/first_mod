import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
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
    public static RegistryObject<Item> bigBrid = ITEMS.register("big_brid",() -> {
        return new BigBrid((new Item.Properties()).maxDamage(384).group(ModGroup.itemGroup));
    });
    public static RegistryObject<Item> iceSword = ITEMS.register("ice_sword",() -> {
        return new IceSword();
    });


    public static final DeferredRegister<Block> BLOCK = new DeferredRegister<>(ForgeRegistries.BLOCKS,"examplemod");
    public static RegistryObject<Block> obsidianBlock = BLOCK.register("obsidian_block",()->{
        return new ObsidianBlock();
    });

    public static RegistryObject<Item> obsidianBlockItem = ITEMS.register("obsidian_block",()->{    //创建一个item，其实没有item也有block
        return new BlockItem(ExampleModItemRegistry.obsidianBlock.get(),new Item.Properties().group(ModGroup.itemGroup));
    });
}

package charge.registry;

import charge.item.ChargeBaseIngot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {
    //itemע����
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS,"charge");

    public static ItemGroup itemGroup = new ChargeGroup();

    //ע���һ��Item
    public static RegistryObject<Item> chargeBaseIngot = ITEMS.register("charge_base_ingot", () ->{
        return new ChargeBaseIngot();
    });
}

package charge.item;

import charge.registry.ChargeGroup;
import charge.registry.ItemRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ChargeBaseIngot extends Item {
    public ChargeBaseIngot() {
        super(new Properties().group(ItemRegistry.itemGroup));
    }
}

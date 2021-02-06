package charge.registry;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ChargeGroup extends ItemGroup {
    public ChargeGroup() {
        super("charge_group");
    }
    @Override
    public ItemStack createIcon() {
        return new ItemStack(ItemRegistry.chargeBaseIngot.get());
    }
}

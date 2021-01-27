import net.minecraft.item.Food;
import net.minecraft.item.Foods;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.common.thread.EffectiveSide;

import java.util.function.Supplier;

public class ObsidianApple extends Item {
    public ObsidianApple() {
        super(new Properties().food(food).group(ItemGroup.FOOD));
    }

    private static EffectInstance effectInstance = new EffectInstance(Effects.STRENGTH, 4*20, 3);
    private static Food food = (new Food.Builder()).fastToEat().saturation(1).hunger(10).effect(()-> effectInstance,1).build();
}

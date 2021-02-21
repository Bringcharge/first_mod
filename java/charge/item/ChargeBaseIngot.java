package charge.item;

import charge.manager.Instruction;
import charge.manager.InstructionsManager;
import charge.manager.InstructionsModel;
import charge.registry.ChargeGroup;
import charge.registry.ItemRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ChargeBaseIngot extends Item {
    public ChargeBaseIngot() {
        super(new Properties().group(ItemRegistry.itemGroup));
    }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        InstructionsModel model = new InstructionsModel();
        model.user = playerIn;
        model.holder = this;
        Instruction ins = new Instruction();
        ins.str = "F002#V103#V101#DI4#{Fif#B001#E201#{F001#V201#}{F003#E101#V201#}}";
        InstructionsManager.functionWithString(ins, model);    //ÃüÁî×Ö·û´®£¬´«Èë£¬À×Åü×Ô¼º

        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }
}

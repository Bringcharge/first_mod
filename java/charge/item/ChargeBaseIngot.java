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
        ins.str = "F001#V011#V102#V002#V101#I10#";
        InstructionsManager.functionWithString(ins, model);    //命令字符串，传入，雷劈自己

        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }
}

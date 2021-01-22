package com.newcharge.demonstration;

import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class LightningMaker extends Item {
    public LightningMaker() {
        super(new Properties().group(ItemGroup.MATERIALS));
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        if(playerIn.getEntityWorld() instanceof ServerWorld) {
            BlockPos position = playerIn.getPosition();
            LightningBoltEntity lightning = new LightningBoltEntity(worldIn, position.getX(), position.getY(), position.getZ(), false);
            ((ServerWorld)playerIn.getEntityWorld()).addLightningBolt(lightning);
            if(!playerIn.abilities.isCreativeMode) {
                itemStack.shrink(1);    //物品数量-1
            }
        }
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));    //回传物品数量
    }
}

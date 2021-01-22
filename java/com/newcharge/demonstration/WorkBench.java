package com.newcharge.demonstration;


import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class WorkBench {
    private static final EffectInstance e = new EffectInstance(Effects.WATER_BREATHING, 100 * 20);
    private static final Item item = Items.BOW;
}

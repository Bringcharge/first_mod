package com.newcharge.demonstration;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.item.Item;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, "demonstration");
    public static RegistryObject<Item> whatIngot = ITEMS.register("what_ingot", WhatIngot::new);
    public static RegistryObject<Item> lightningMaker = ITEMS.register("lightning_maker", LightningMaker::new);
}
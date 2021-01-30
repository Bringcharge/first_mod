import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.Position;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ObsidianSword extends SwordItem {
    private static IItemTier iItemTier = new IItemTier() {
        @Override
        public int getMaxUses() {
            return 0;
        }

        @Override
        public float getEfficiency() {
            return 20.f;
        }

        @Override
        public float getAttackDamage() {
            return 10.f;
        }

        @Override
        public int getHarvestLevel() {
            return 3;
        }

        @Override
        public int getEnchantability() {
            return 30;
        }

        @Override
        public Ingredient getRepairMaterial() {
            return Ingredient.fromItems(ExampleModItemRegistry.obsidianIngot.get());
        }
    };
    public ObsidianSword () {
        super(iItemTier,5, -2.f, new Item.Properties().group(ItemGroup.COMBAT));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
//        if (playerIn.getEntityWorld() instanceof ServerWorld) {
//
//            Vec3d vec3d = playerIn.getLook(1.f).normalize();    //视线向量
//            Vec3d vecPlayerEye = playerIn.getPositionVec().add(0,playerIn.getEyeHeight(),0);    //一定要算tmd眼睛
//
//            Vec3d vec3d1 = vec3d.scale(50.f).add(vecPlayerEye);   //放大长度，并且从玩家位置指出去，草，tmd一定要加上眼睛高度!!!
//
//            //查看阻塞位置
//            BlockRayTraceResult blockRayTraceResult = worldIn.rayTraceBlocks(new RayTraceContext(vecPlayerEye, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, playerIn));
//            //输出阻塞位置
//            System.out.println(blockRayTraceResult.getHitVec());
//            Vec3d finalVector = blockRayTraceResult.getHitVec();
//
//            LightningBoltEntity lightning = new LightningBoltEntity(worldIn, finalVector.getX(), finalVector.getY(), finalVector.getZ(), false);
//            ((ServerWorld) playerIn.getEntityWorld()).addLightningBolt(lightning);
//        }
        playerIn.setActiveHand(handIn);
        return ActionResult.resultConsume( playerIn.getHeldItem(handIn));
//        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            boolean flag = true;    //反正是个flag，当作我是创造模式就行
            ItemStack itemstack = playerentity.findAmmo(stack);

//            int i = this.getUseDuration(stack) - timeLeft;
//            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, !itemstack.isEmpty() || flag);
//            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) { //反正有了flag
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = 3.f;  //好像是速度，应该是最大是1？
                if (!((double)f < 0.1D)) {  //速度足够大
                    boolean flag1 = true;   //先假设走创造模式
                    if (!worldIn.isRemote) {
                        ArrowItem arrowitem = (ArrowItem)(Items.ARROW); //最原版的箭矢
                        AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
//                        abstractarrowentity = customeArrow(abstractarrowentity);abstractarrowentity   //不懂干嘛的

                        //那个p_184547_4_ 是个无用参数，并没有实际调用，最后一个参数是不准确性
                        abstractarrowentity.shoot(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, f * 3.0F, 1.0F);

                            abstractarrowentity.setIsCritical(true);    //箭后面是否带有大量暴击粒子

                        //下面一系列的附魔检测，暂时不处理
//                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
//                        if (j > 0) {
//                            abstractarrowentity.setDamage(abstractarrowentity.getDamage() + (double)j * 0.5D + 0.5D);
//                        }
//
//                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
//                        if (k > 0) {
//                            abstractarrowentity.setKnockbackStrength(k);
//                        }
//
//                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
//                            abstractarrowentity.setFire(100);
//                        }

                        stack.damageItem(1, playerentity, (p_220009_1_) -> {
                            p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                        });

                        //设置是否能够捡起
//                        if (flag1 || playerentity.abilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                            abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
//                        }

                        worldIn.addEntity(abstractarrowentity);
                    }
                    //声音设置
                    worldIn.playSound((PlayerEntity)null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                    if (!flag1 && !playerentity.abilities.isCreativeMode) { //删除箭矢的函数
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            playerentity.inventory.deleteStack(itemstack);
                        }
                    }
                    //这tm是啥？
//                    playerentity.addStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (p_220045_0_) -> {
            p_220045_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        Vec3d finalVector = target.getPositionVector();
        if (attacker.getEntityWorld() instanceof ServerWorld) {
            LightningBoltEntity lightning = new LightningBoltEntity(attacker.getEntityWorld(), finalVector.getX(), finalVector.getY(), finalVector.getZ(), false);
            ((ServerWorld) attacker.getEntityWorld()).addLightningBolt(lightning);
        }
        return true;
    }

}

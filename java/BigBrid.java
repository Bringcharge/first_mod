import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.BiConsumer;

public class BigBrid extends BowItem {

    public BigBrid(Item.Properties builder) {
        super(builder);
    }

    //箭矢的实例内部类
    public class ObsidianArrowEntity extends ArrowEntity {
        public ObsidianArrowEntity(EntityType<? extends ArrowEntity> type, World worldIn) {
            super(type, worldIn);
        }
        public ObsidianArrowEntity(World worldIn, double x, double y, double z) {
            super(worldIn,x,y,z);
        }
        public ObsidianArrowEntity(World worldIn, LivingEntity shooter) { super(worldIn,shooter); }

        private BiConsumer<Integer, Vec3d> biConsumer;

        public void setBack(BiConsumer<Integer, Vec3d> biConsumer) {
            this.biConsumer = biConsumer;
        }



        @Override
        protected void arrowHit(LivingEntity living) {
            if (this.biConsumer != null) {
                this.biConsumer.accept(120, living.getPositionVec());
            }
        }
    }

    private int delayTick;
    private int arrowNumber;
    private Vec3d target3d;

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if (this.delayTick > 0) {
            this.delayTick--;
        }
        else if (this.arrowNumber > 0) {
            this.arrowNumber--;
            this.creatArrowItem(this.target3d,entityIn.world);
            this.delayTick = 1;
        }
    }

    //箭雨的造箭方法
    private void creatArrowItem(Vec3d target3d, World worldI) {
        Vec3d arrowCreatPosition = target3d.add((random.nextGaussian() -0.5) * 8.f ,20, (random.nextGaussian() -0.5) * 8.f);
        Vec3d vectorToTarget = target3d.add(arrowCreatPosition.inverse());

        //创建箭矢
        ArrowItem arrowitem = (ArrowItem)(Items.ARROW); //最原版的箭矢
        AbstractArrowEntity abstractarrowentity = new ArrowEntity(worldI ,arrowCreatPosition.getX(),arrowCreatPosition.getY(),arrowCreatPosition.getZ());

        //设置初速度和散布
        abstractarrowentity.shoot(vectorToTarget.getX(),vectorToTarget.getY(),vectorToTarget.getZ(),2.0f,3.f);
//        abstractarrowentity.shoot(0,-1,0,2.0f,3.f);
        abstractarrowentity.setIsCritical(false);    //箭后面是否带有大量暴击粒子
        worldI.addEntity(abstractarrowentity);
    }

    //射箭方法
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            boolean flag = playerentity.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemstack = playerentity.findAmmo(stack);

            int i = this.getUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getArrowVelocity(i);
                if (!((double)f < 0.1D)) {
                    boolean flag1 = playerentity.abilities.isCreativeMode || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, playerentity));
                    if (!worldIn.isRemote) {
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        ObsidianArrowEntity abstractarrowentity = new ObsidianArrowEntity(worldIn,playerentity);

                        //设置击中后的函数
                        abstractarrowentity.setBack((a,b)->{
                            this.arrowNumber = a;
                            this.target3d = b;
                        });

                        abstractarrowentity.shoot(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, f * 9.0F, 1.0F);
                        if (f == 1.0F) {
                            abstractarrowentity.setIsCritical(true);
                        }
//                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
//                        if (j > 0) {
//                            abstractarrowentity.setDamage(abstractarrowentity.getDamage() + (double)j * 0.5D + 0.5D);
//                        }

                        //击退
//                        abstractarrowentity.setKnockbackStrength(-5);


//                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
//                            abstractarrowentity.setFire(100);
//                        }

//                        stack.damageItem(1, playerentity, (p_220009_1_) -> {
//                            p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
//                        });
                        if (flag1 || playerentity.abilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                            abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED;
                        }

                        worldIn.addEntity(abstractarrowentity);
                    }

                    worldIn.playSound((PlayerEntity)null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !playerentity.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            playerentity.inventory.deleteStack(itemstack);
                        }
                    }

                    playerentity.addStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }
}

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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BigBrid extends BowItem {

    public BigBrid(Item.Properties builder) {
        super(builder);
    }

    //��ʸ��ʵ���ڲ���
    public class ObsidianArrowEntity extends ArrowEntity {
        public ObsidianArrowEntity(EntityType<? extends ArrowEntity> type, World worldIn) {
            super(type, worldIn);
        }
        public ObsidianArrowEntity(World worldIn, double x, double y, double z) {
            super(worldIn,x,y,z);
        }
        public ObsidianArrowEntity(World worldIn, LivingEntity shooter) { super(worldIn,shooter); }

        private BiConsumer<Integer, Vec3d> biConsumer;
        private Consumer<Vec3d> consumer;

        public void setBack(BiConsumer<Integer, Vec3d> biConsumer) {
            this.biConsumer = biConsumer;
        }
        public void setConsumer(Consumer<Vec3d> consumer) {this.consumer = consumer;}

//        @Override
//        protected void arrowHit(LivingEntity living) {
//            if (this.biConsumer != null) {
//                this.biConsumer.accept(120, living.getPositionVec());
//            }
//        }

        @Override
        protected void onHit(RayTraceResult raytraceResultIn) {
            super.onHit(raytraceResultIn);
            RayTraceResult.Type raytraceresult$type = raytraceResultIn.getType();
            if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
                EntityRayTraceResult entity = (EntityRayTraceResult)raytraceResultIn;
                if (this.biConsumer != null) {
                    this.biConsumer.accept(120, entity.getEntity().getPositionVec());
                }
            } else if (raytraceresult$type == RayTraceResult.Type.BLOCK) {
                BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceResultIn;
                Vec3d vec3d = blockraytraceresult.getHitVec();
                if (this.biConsumer != null) {
                    this.consumer.accept(vec3d);
                }
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

    //������������
    private void creatArrowItem(Vec3d target3d, World worldI) {
        Vec3d arrowCreatPosition = target3d.add((random.nextGaussian() -0.5) * 8.f ,20, (random.nextGaussian() -0.5) * 8.f);
        Vec3d vectorToTarget = target3d.add(arrowCreatPosition.inverse());

        //������ʸ
        ArrowItem arrowitem = (ArrowItem)(Items.ARROW); //��ԭ��ļ�ʸ
        AbstractArrowEntity abstractarrowentity = new ArrowEntity(worldI ,arrowCreatPosition.getX(),arrowCreatPosition.getY(),arrowCreatPosition.getZ());

        //���ó��ٶȺ�ɢ��
        abstractarrowentity.shoot(vectorToTarget.getX(),vectorToTarget.getY(),vectorToTarget.getZ(),2.0f,3.f);
//        abstractarrowentity.shoot(0,-1,0,2.0f,3.f);
        abstractarrowentity.setIsCritical(false);    //�������Ƿ���д�����������
        worldI.addEntity(abstractarrowentity);
    }

    private void flash(LivingEntity livingEntity, Vec3d targetPlace) {
        World world = livingEntity.world;
        if (!world.isRemote) {
            livingEntity.setPositionAndUpdate(targetPlace.getX(), targetPlace.getY(), targetPlace.getZ());
            livingEntity.fallDistance = 0.0F;
        }
    }

    //�������
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

                        //���û��й����ĺ���
                        abstractarrowentity.setBack((a,b)->{
                            this.arrowNumber = a;
                            this.target3d = b;
                        });

                        //���е���ʱ��ĺ���
                        abstractarrowentity.setConsumer((e)->{
                            this.flash(entityLiving,e);
                        });

                        abstractarrowentity.shoot(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, f * 9.0F, 1.0F);
                        if (f == 1.0F) {
                            abstractarrowentity.setIsCritical(true);
                        }
//                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
//                        if (j > 0) {
//                            abstractarrowentity.setDamage(abstractarrowentity.getDamage() + (double)j * 0.5D + 0.5D);
//                        }

                        //����
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

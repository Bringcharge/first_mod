import net.minecraft.dispenser.Position;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ObsidianIngot extends Item {
    public ObsidianIngot() {
        super(new Properties().group(ModGroup.itemGroup));
    }

    //��ʸ��ʵ���ڲ���
    public class ObsidianArrowEntity extends ArrowEntity {
        public ObsidianArrowEntity(EntityType<? extends ArrowEntity> type, World worldIn) {
            super(type, worldIn);
        }
        public ObsidianArrowEntity(World worldIn, double x, double y, double z) {
            super(worldIn,x,y,z);
        }

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

    public void  setTarget3d (Vec3d target3d) {
        this.target3d = target3d;
    }
    public void setArrowNumber (int arrowNumber) {
        this.arrowNumber = arrowNumber;
    }

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

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
//        if (playerIn.getEntityWorld() instanceof ServerWorld) {
//
            Vec3d vec3d = playerIn.getLook(1.f).normalize();    //��������
            Vec3d vecPlayerEye = playerIn.getPositionVec().add(0,playerIn.getEyeHeight(),0);    //һ��Ҫ��tmd�۾�

            Vec3d vec3d1 = vec3d.scale(50.f).add(vecPlayerEye);   //�Ŵ󳤶ȣ����Ҵ����λ��ָ��ȥ���ݣ�tmdһ��Ҫ�����۾��߶�!!!

            //�鿴����λ��
            BlockRayTraceResult blockRayTraceResult = worldIn.rayTraceBlocks(new RayTraceContext(vecPlayerEye, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, playerIn));
            //�������λ��
            System.out.println(blockRayTraceResult.getHitVec());
            Vec3d finalVector = blockRayTraceResult.getHitVec();
//
//            LightningBoltEntity lightning = new LightningBoltEntity(worldIn, finalVector.getX(), finalVector.getY(), finalVector.getZ(), false);
//            ((ServerWorld) playerIn.getEntityWorld()).addLightningBolt(lightning);
//       }
        ItemStack stack = playerIn.getHeldItem(handIn);
        PlayerEntity playerentity = playerIn;
        boolean flag = true;    //�����Ǹ�flag���������Ǵ���ģʽ����
        ItemStack itemstack = playerentity.findAmmo(stack);

//            int i = this.getUseDuration(stack) - timeLeft;
//            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, !itemstack.isEmpty() || flag);
//            if (i < 0) return;

        if (!itemstack.isEmpty() || flag) { //��������flag
            if (itemstack.isEmpty()) {
                itemstack = new ItemStack(Items.ARROW);
            }

            float f = 3.f;  //�������ٶȣ�Ӧ���������1��
            if (!((double)f < 0.1D)) {  //�ٶ��㹻��
                boolean flag1 = true;   //�ȼ����ߴ���ģʽ
                if (!worldIn.isRemote) {
                    ArrowItem arrowitem = (ArrowItem)(Items.ARROW); //��ԭ��ļ�ʸ
                    ObsidianArrowEntity abstractarrowentity = new ObsidianArrowEntity(worldIn,finalVector.getX(),finalVector.getY(),finalVector.getZ());
                    abstractarrowentity.setBack((a,b)->{
                        this.arrowNumber = a;
                        this.target3d = b;
                    });
                            //arrowitem.createArrow(worldIn, itemstack, playerentity);
//                        abstractarrowentity = customeArrow(abstractarrowentity);abstractarrowentity   //���������

                    //�Ǹ�p_184547_4_ �Ǹ����ò�������û��ʵ�ʵ��ã����һ�������ǲ�׼ȷ��
//                    abstractarrowentity.shoot(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, f * 3.0F, 1.0F);
                    abstractarrowentity.shoot(-vec3d.getX(),-vec3d.getY(),-vec3d.getZ(),f * 1.0f,0.f);
                    abstractarrowentity.setIsCritical(true);    //�������Ƿ���д�����������

                    //����һϵ�еĸ�ħ��⣬��ʱ������
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
                    //����Ʒ���;�
//                    stack.damageItem(1, playerentity, (p_220009_1_) -> {
//                        p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
//                    });

                    //�����Ƿ��ܹ�����
//                        if (flag1 || playerentity.abilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                    abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.DISALLOWED;
//                        }

                    worldIn.addEntity(abstractarrowentity);
                }
                //��������
                worldIn.playSound((PlayerEntity)null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                if (!flag1 && !playerentity.abilities.isCreativeMode) { //ɾ����ʸ�ĺ���
                    itemstack.shrink(1);
                    if (itemstack.isEmpty()) {
                        playerentity.inventory.deleteStack(itemstack);
                    }
                }
                //��tm��ɶ��
//                    playerentity.addStat(Stats.ITEM_USED.get(this));
            }
        }
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }
}

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class IceSword extends SwordItem {
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
    public IceSword() {
        super(iItemTier,3,2.f,new Item.Properties().group(ModGroup.itemGroup));
    }

    private int skillTick = 0;

    //ȷ���������Ƿ���ڸ�entity
    private boolean inSight(PlayerEntity player, LivingEntity livingEntity) {
        Vec3d vec3d = player.getLook(1.0F).normalize();
        Vec3d vec3d1 = new Vec3d(player.getPosX() - livingEntity.getPosX(), player.getPosYEye() - livingEntity.getPosYEye(), player.getPosZ() - livingEntity.getPosZ()).inverse();
        double d0 = vec3d1.length();
        vec3d1 = vec3d1.normalize();
        double d1 = vec3d.dotProduct(vec3d1);
//        if (player.canEntityBeSeen(livingEntity)) {
//            System.out.print("bringCharge: can see");
//            System.out.println(livingEntity.getClass().getCanonicalName());
//        }
        //����ܿ�����entity�кܶడ����������������Ļ�ھ����ܿ����ģ������Ҳ�֪����û�б���ס���߼�
        //��������ˮ�￴һ�ۣ�ȫ����
        return d1 > 1.0D - 0.025D / d0 ? player.canEntityBeSeen(livingEntity) : false;

    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if (this.skillTick > 0) {
            this.skillTick--;
            if (this.skillTick == 0) {
                //������ä
                if (entityIn instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entityIn;
                    player.addPotionEffect(new EffectInstance(Effects.BLINDNESS,20* 5, 1));
                }
            }
        } else {
            return;
        }
        if (this.skillTick % 1 == 0) {   //���ڶ���4tickһ��
            if (worldIn instanceof ServerWorld) {   //���ȱ�֤����serverWord��
                ServerWorld serverWorld = (ServerWorld) worldIn;
                serverWorld.getEntities().forEach(entity -> {
                    if (entity instanceof CreatureEntity) { //�����ж�
                        //ǿ������ת��
                        CreatureEntity creatureEntity = (CreatureEntity)entity;
                        if (this.inSight((PlayerEntity)entityIn,creatureEntity) ) { //�ܹ�����
                            creatureEntity.attackEntityFrom(DamageSource.IN_FIRE,6);
                            creatureEntity.addPotionEffect( new EffectInstance(Effects.WITHER,3*20,3)); //���ø�ħ״̬
                            creatureEntity.setFire(90);
                        }
                    }
                });
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (worldIn instanceof ServerWorld) {
            if (this.skillTick <= 0 ) {
                if (playerIn.getActivePotionEffect(Effects.BLINDNESS)!= null) {
                    System.out.println("��ʵ���ǿյģ����Ǿ��ǳ���������");
                }
                this.skillTick = 101;
                return ActionResult.resultConsume(playerIn.getHeldItem(handIn));
            }
        }
        return ActionResult.resultFail(playerIn.getHeldItem(handIn));
    }
}

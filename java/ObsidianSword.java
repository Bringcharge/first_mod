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
        if (playerIn.getEntityWorld() instanceof ServerWorld) {

            Vec3d vec3d = playerIn.getLook(1.f).normalize();    //视线向量
            Vec3d vecPlayerEye = playerIn.getPositionVec().add(0,playerIn.getEyeHeight(),0);    //一定要算tmd眼睛

            Vec3d vec3d1 = vec3d.scale(50.f).add(vecPlayerEye);   //放大长度，并且从玩家位置指出去，草，tmd一定要加上眼睛高度!!!

            //查看阻塞位置
            BlockRayTraceResult blockRayTraceResult = worldIn.rayTraceBlocks(new RayTraceContext(vecPlayerEye, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, playerIn));
            //输出阻塞位置
            System.out.println(blockRayTraceResult.getHitVec());
            Vec3d finalVector = blockRayTraceResult.getHitVec();

            LightningBoltEntity lightning = new LightningBoltEntity(worldIn, finalVector.getX(), finalVector.getY(), finalVector.getZ(), false);
            ((ServerWorld) playerIn.getEntityWorld()).addLightningBolt(lightning);
        }
        playerIn.setActiveHand(handIn);
        return ActionResult.resultConsume( playerIn.getHeldItem(handIn));
//        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
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

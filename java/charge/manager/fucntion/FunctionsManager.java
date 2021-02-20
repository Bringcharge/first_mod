package charge.manager.fucntion;

import charge.entity.ChargeArrow;
import charge.manager.Instruction;
import charge.manager.InstructionsManager;
import charge.manager.InstructionsModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FunctionsManager {
    //落雷
    public static void lightningDown (Vec3d targetPlace, InstructionsModel model) {    //F001#
        World worldIn = model.user.world;
        if (worldIn != null && worldIn instanceof ServerWorld) {
            LightningBoltEntity lightning = new LightningBoltEntity(worldIn, targetPlace.getX(), targetPlace.getY(), targetPlace.getZ(), false);
            ((ServerWorld) worldIn).addLightningBolt(lightning);
        }
    }

    //射箭
    public static void ShotArrow(Vec3d start, Vec3d direction, double speed, String blockInstructions, InstructionsModel model) { //F002#
        World worldIn = model.user.world;
        if (worldIn != null && worldIn instanceof ServerWorld) {
            ChargeArrow chargeArrow = new ChargeArrow(worldIn, start.getX(), start.getY(), start.getZ());
            chargeArrow.setBack((instruction,instructionsModel)->{
                //参数注入
                instruction.str = blockInstructions;
                instructionsModel.copy(model);
            });
            chargeArrow.shoot(direction.getX(),direction.getY(),direction.getZ(), (float) speed,0.f);
            worldIn.addEntity(chargeArrow);
        }
    }

    //传送，把entity传送到targetPlace
    public static void deliver(LivingEntity livingEntity, Vec3d targetPlace, InstructionsModel model) {
        if (livingEntity==null) {
            return;
        }
        World world = livingEntity.world;
        if (!world.isRemote) {
            livingEntity.setPositionAndUpdate(targetPlace.getX(), targetPlace.getY(), targetPlace.getZ());
            livingEntity.fallDistance = 0.0F;
        }
    }

    //if函数
    public static void logicIf(boolean condition, String thenCondition, String elseCondition,InstructionsModel model) { //Fif#
        InstructionsModel newModel = new InstructionsModel();
        newModel.copy(model);
        Instruction instruction = new Instruction();

        if (condition) {    //if 后面的条件是true
            instruction.str = thenCondition;
            InstructionsManager.functionWithString(instruction,newModel);
        }else {
            instruction.str = elseCondition;
            InstructionsManager.functionWithString(instruction,newModel);
        }
    }
}



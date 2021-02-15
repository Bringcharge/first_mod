package charge.manager.fucntion;

import charge.entity.ChargeArrow;
import charge.manager.InstructionsModel;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FunctionsManager {
    //ÂäÀ×
    public static void lightningDown (Vec3d targetPlace, InstructionsModel model) {    //F001#
        World worldIn = model.user.world;
        if (worldIn != null && worldIn instanceof ServerWorld) {
            LightningBoltEntity lightning = new LightningBoltEntity(worldIn, targetPlace.getX(), targetPlace.getY(), targetPlace.getZ(), false);
            ((ServerWorld) worldIn).addLightningBolt(lightning);
        }
    }

    //Éä¼ý
    public static void ShotArrow(Vec3d start, Vec3d direction, double speed, String blockInstructions, InstructionsModel model) { //F002#
        World worldIn = model.user.world;
        if (worldIn != null && worldIn instanceof ServerWorld) {
            ChargeArrow chargeArrow = new ChargeArrow(worldIn, start.getX(), start.getY(), start.getZ());
            chargeArrow.setBack((instruction,instructionsModel)->{
                //²ÎÊý×¢Èë
                instruction.str = blockInstructions;
                instructionsModel.holder = model.holder;
                instructionsModel.user = model.user;
            });
            chargeArrow.shoot(direction.getX(),direction.getY(),direction.getZ(), (float) speed,0.f);
            worldIn.addEntity(chargeArrow);
        }
    }
}



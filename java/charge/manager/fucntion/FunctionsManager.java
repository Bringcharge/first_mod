package charge.manager.fucntion;

import charge.manager.InstructionsModel;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FunctionsManager {
    public static void lightningDown (Vec3d targetPlace, InstructionsModel model) {    //F001#
        World worldIn = model.user.world;
        if (worldIn != null && worldIn instanceof ServerWorld) {
            LightningBoltEntity lightning = new LightningBoltEntity(worldIn, targetPlace.getX(), targetPlace.getY(), targetPlace.getZ(), false);
            ((ServerWorld) worldIn).addLightningBolt(lightning);
        }
    }
}

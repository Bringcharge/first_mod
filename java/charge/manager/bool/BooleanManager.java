package charge.manager.bool;

import charge.manager.InstructionsModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BooleanManager {
    //�ж�һ��entity����null
    public static boolean entityNotNull (Entity entity, InstructionsModel model) {    //B001#
       return entity!=null;
    }

    //�ж�һ��vector����null
}

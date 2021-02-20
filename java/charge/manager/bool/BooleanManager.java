package charge.manager.bool;

import charge.manager.InstructionsModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BooleanManager {
    //判断一个entity不是null
    public static boolean entityNotNull (Entity entity, InstructionsModel model) {    //B001#
       return entity!=null;
    }

    //判断一个vector不是null
}

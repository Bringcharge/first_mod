package charge.manager.entity;

import charge.manager.InstructionsModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EntityManager {
    //user实体
    public static Entity entityOfUser (InstructionsModel model) {    //E101#
        return model.user;
    }
    //从block中取出实体
    public static Entity entityBlockTarget (InstructionsModel model) {    //E201#
        return model.targetEntity;
    }
}

package me.tias.blockeygoaltender;

import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

public class Utilities {

    static BlockeyGoaltender plugin = BlockeyGoaltender.getInstance();

    public Utilities() {
        plugin = BlockeyGoaltender.getInstance();
    }

    public static float newYaw(float Number) {
        float yaw;
        if (Number < 0) {
            yaw = 360 + Number;
        } else if (Number > 360) {
            yaw = (Number - 360);
        } else {
            yaw = Number;
        }
        return yaw;
    }

    public static void removeAll() {
        for (Entity entity : plugin.getServer().getWorlds().get(0).getEntities()) {
            if (entity instanceof ArmorStand stand && stand.getScoreboardTags().contains("GOALIEPAD")) {
                stand.remove();
            }
        }
    }

}

package me.tias.blockeygoaltender;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GoalieManager {

    private static Goalie goalie;
    private static final Map<UUID, Goalie> goalieList = new HashMap<>();

    public static Goalie createGoalie(Player player) {
        goalie = new Goalie(player);

        if (goalieList.containsKey(player.getUniqueId())) {
            goalieList.replace(player.getUniqueId(), goalie);
        } else {
            goalieList.put(player.getUniqueId(), goalie);
        }

        goalie.createPads();

        return goalie;
    }

    public static void removeGoalie(Player player) {
        UUID uuid = player.getUniqueId();
        if (goalieList.containsKey(uuid)) {
            goalieList.remove(uuid);
        }
    }

    public static Goalie getSelected(UUID uuid) {
        return goalieList.get(uuid);
    }

    public static Map<UUID, Goalie> getGoalieList() {
        return goalieList;
    }

    public void test() {
        goalieList.forEach((uuid, goalie1) -> goalie1.teleportPads());
    }
}

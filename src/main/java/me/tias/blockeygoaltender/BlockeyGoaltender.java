package me.tias.blockeygoaltender;

import jdk.jshell.execution.Util;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class BlockeyGoaltender extends JavaPlugin {

    static BlockeyGoaltender instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        Utilities.removeAll();
        new Listener();
        goalieRunnable();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Utilities.removeAll();
    }

    public static BlockeyGoaltender getInstance() {
        return instance;
    }

    public void goalieRunnable() {
        new BukkitRunnable() {
            @Override
            public void run() {

                Map<UUID, Goalie> goalieList = GoalieManager.getGoalieList();

                goalieList.forEach((uuid, goalie1) -> {
                    if (goalie1.get().getScoreboardTags().contains("goalie")) {
                        if (!goalie1.get().isOnline()) {
                            // logic to remove pads
                            goalie1.killPads();
                        } else {
                            // goalie is online
                            goalie1.teleportPads();
                        }
                    }
                });

            }
        }.runTaskTimer(this, 0, 1);
    }
}

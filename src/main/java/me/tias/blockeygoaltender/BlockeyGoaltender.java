package me.tias.blockeygoaltender;

import jdk.jshell.execution.Util;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlockeyGoaltender extends JavaPlugin {

    static BlockeyGoaltender instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        Utilities.removeAll();
        new Listener();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Utilities.removeAll();
    }

    public static BlockeyGoaltender getInstance() {
        return instance;
    }
}

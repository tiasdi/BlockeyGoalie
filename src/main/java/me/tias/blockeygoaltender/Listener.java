package me.tias.blockeygoaltender;

import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class Listener implements org.bukkit.event.Listener {

    BlockeyGoaltender plugin;

    public Listener() {
        plugin = BlockeyGoaltender.getInstance();
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    /*@EventHandler
    public void playerSneak(PlayerToggleSneakEvent e) {
        if (e.getPlayer().getScoreboardTags().contains("goalie")) {
            Goalie goalie = GoalieManager.getSelected(e.getPlayer().getUniqueId());
            goalie.teleportPads();
            goalie.startPadTask();
        }
    } */

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Goalie goalie = GoalieManager.createGoalie(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Goalie goalie = GoalieManager.getSelected(e.getPlayer().getUniqueId());
        goalie.killPads();
        GoalieManager.removeGoalie(e.getPlayer());
        e.getPlayer().getScoreboardTags().remove("goalie");
    }

    @EventHandler
    public void onPuckMove(EntityMoveEvent e) {
        if (e.getEntity() instanceof Endermite puck) {
            puck.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,5555,255,false,false));
            puck.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION,5555,255,false,false));
            puck.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,5555,255,false,false));

            if (String.valueOf(puck.getLocation().getBlock().getRelative(BlockFace.DOWN).getType()).toUpperCase(Locale.ROOT).contains("ICE")) {
                Location loc = puck.getLocation().clone();
                double yDisToGround = puck.getLocation().getY() - Math.floor(puck.getLocation().getY());
                if (yDisToGround < 0.175) {
                    for (Entity entity : puck.getNearbyEntities(0.27 + Math.abs(puck.getVelocity().getX()/1.8), 0.4, 0.27 + Math.abs(puck.getVelocity().getZ())/1.8)) {
                        if (entity instanceof ArmorStand stand && stand.getEquipment().getBoots().getType().equals(Material.LEATHER_BOOTS)) {
                            puck.setVelocity(stand.getLocation().getDirection().multiply(0.15));
                        }
                    }
                } else if (yDisToGround < 0.325) {
                    for (Entity entity : puck.getNearbyEntities(0.27 + Math.abs(puck.getVelocity().getX()), 0.4, 0.27 + Math.abs(puck.getVelocity().getZ()))) {
                        if (entity instanceof ArmorStand stand && stand.getEquipment().getBoots().getType().equals(Material.LEATHER_BOOTS)) {
                            puck.setVelocity(stand.getLocation().getDirection().multiply(0.25));
                            @NotNull Vector vec = puck.getVelocity();
                            vec.setY(0.275);
                            puck.setVelocity(vec);
                        }
                    }
                }
            } else if (e.getEntity() instanceof Player player && player.getScoreboardTags().contains("goalie")) {
                Goalie goalie = GoalieManager.getSelected(player.getUniqueId());
                goalie.teleportPads();
            }
        }
    }

}

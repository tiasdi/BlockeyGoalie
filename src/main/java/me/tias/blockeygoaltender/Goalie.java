package me.tias.blockeygoaltender;

import com.destroystokyo.paper.ParticleBuilder;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Goalie {

    private final BlockeyGoaltender plugin;
    private final Player player;
    private boolean isSneaking = false;
    private World world;
    private ArmorStand leftPad;
    private ArmorStand rightPad;
    private final ItemStack pads = new ItemStack(Material.LEATHER_BOOTS);
    private final LeatherArmorMeta meta = (LeatherArmorMeta) pads.getItemMeta();


    public Goalie(@NotNull final Player player) {
        this.plugin = BlockeyGoaltender.getInstance();
        this.player = player;
        this.world = player.getWorld();
    }

    public Player get() {
        return player;
    }

    public void teleportPads() {

        if (player.isSneaking()) {
            if (!padsDisplayed()) {
                displayPads(true);
            }
        } else {
            displayPads(false);
        }

            Location teleport;
            teleport = getLocation().clone();
            teleport.setPitch(0);
            teleport.setYaw(Utilities.newYaw(getLocation().getYaw() - 90));
            teleport.add(teleport.getDirection().multiply(-0.1));
            teleport.setYaw(Utilities.newYaw(getLocation().getYaw() + 33));
            teleport.add(teleport.getDirection().multiply(0.33));

            leftPad.teleport(teleport);
            teleport = getLocation().clone();
            teleport.setPitch(0);
            teleport.setYaw(Utilities.newYaw(getLocation().getYaw() + 90));
            teleport.add(teleport.getDirection().multiply(-0.1));
            teleport.setYaw(Utilities.newYaw(getLocation().getYaw() - 33));
            teleport.add(teleport.getDirection().multiply(0.33));
            rightPad.teleport(teleport);

    }

    public void createPads() {
        world = player.getWorld();
        this.leftPad = (ArmorStand) world.spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        this.rightPad = (ArmorStand) world.spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        setupPad(leftPad);
        setupPad(rightPad);
    }

    public void killPads() {
        leftPad.remove();
        rightPad.remove();
    }

    public void setupPad(ArmorStand stand) {
        meta.setColor(Color.BLACK);
        pads.setItemMeta(meta);
        stand.setMarker(true);
        stand.setInvulnerable(true);
        stand.setBasePlate(false);
        stand.setInvisible(true);
        stand.setSilent(true);
        stand.getScoreboardTags().add("GOALIEPAD");
    }

    public void displayPads(boolean enabled) {
        if (!enabled) {
            leftPad.getEquipment().clear();
            rightPad.getEquipment().clear();
        } else {
            leftPad.getEquipment().setBoots(pads);
            rightPad.getEquipment().setBoots(pads);
        }
    }

    public boolean padsDisplayed() {
        return leftPad.getEquipment().getBoots().equals(Material.LEATHER_BOOTS);
    }

    public Location getLocation() {
        return player.getLocation();
    }






}

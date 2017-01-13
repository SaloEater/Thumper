package com.salo.thumper.listeners;

import com.avaje.ebeaninternal.api.Monitor;
import com.salo.thumper.Main;
import com.salo.thumper.thumper.thumperController;
import com.salo.thumper.utils.Particles;
import com.sk89q.worldedit.bukkit.adapter.BukkitImplAdapter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.apache.logging.log4j.core.net.Priority;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.RunnableFuture;

/**
 * Created by user on 14.12.2016.
 */
public class clickEvent implements Listener{

    private int tCounter = 0;
    private int timer;

    @EventHandler
    public void onClickEvent(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() != Material.AIR) {
                if (e.getPlayer().getItemInHand().getType() == Material.ARMOR_STAND) {
                    if (e.getPlayer().getItemInHand().getItemMeta().getLore()!=null&&e.getPlayer().getItemInHand().getItemMeta().getLore().get(0).equals("Place to start")) {
                        e.setCancelled(true);
                        e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
                        Location drillPlace = e.getClickedBlock().getLocation();
                        drillPlace.clone().getBlock().setType(Material.SPONGE);
                        if(drillPlace.clone().add(0,1,0).getBlock().getType()==Material.AIR) {
                            e.getPlayer().sendMessage("work");
                            ArmorStand armorStand = drillPlace.getWorld().spawn(drillPlace.clone().add(0.5,1,0.5), ArmorStand.class);
                            e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
                            armorStand.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
                            armorStand.setCustomNameVisible(true);
                            armorStand.setVisible(true);
                            new thumperController(armorStand, e.getPlayer().getName(), 60, 0).start();
                            Main.getPlugin().getLogger().info("BeforeDestroy");
                            drillPlace.clone().add(0,1,0).getBlock().setType(Material.AIR);
                        } else {
                            e.getPlayer().sendMessage(String.valueOf(drillPlace.clone().add(0,1,0).getBlock().getType()));
                        }
                    }
                }
            }
        }
    }

}



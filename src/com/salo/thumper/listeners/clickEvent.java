package com.salo.thumper.listeners;

import com.salo.thumper.Main;
import com.salo.thumper.thumper.thumperController;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by user on 14.12.2016.
 */
public class clickEvent implements Listener{

    @EventHandler
    public void onClickEvent(PlayerInteractEvent e){
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK &&
            e.getClickedBlock().getType() != Material.AIR &&
            e.getPlayer().getItemInHand().getType() == Material.ARMOR_STAND &&
            e.getPlayer().getItemInHand().getItemMeta().getLore()!=null &&
            e.getPlayer().getItemInHand().getItemMeta().getLore().get(0).equals("Place to start"))
        {
            e.setCancelled(true);
            if(!Main.getPlugin().activeDrills.contains(e.getPlayer().getName())){
                initDrill(e.getPlayer(), e.getClickedBlock().getLocation());
            } else {
                e.getPlayer().sendMessage(ChatColor.RED + "Дождитесь окончания текущего бура!");
            }
        }
    }

    private void initDrill(Player player, Location drillPlace){
        player.setItemInHand(new ItemStack(Material.AIR));
        drillPlace.clone().getBlock().setType(Material.SPONGE);
        if(drillPlace.clone().add(0,1,0).getBlock().getType()==Material.AIR) {
            ArmorStand armorStand = drillPlace.getWorld().spawn(drillPlace.clone().add(0.5,1,0.5), ArmorStand.class);
            player.setItemInHand(null);
            armorStand.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
            armorStand.setCustomNameVisible(true);
            armorStand.setVisible(true);
            ProtectedRegion drillRegion = new ProtectedCuboidRegion("drillPlace"+player.getName().toLowerCase(), convertToSk89qBV(drillPlace.clone().add(-10, -drillPlace.getBlockY(), -10)), convertToSk89qBV(drillPlace.clone().add(10, 256-drillPlace.getBlockY(), 10)));
            RegionContainer container = Main.getPlugin().WEPlugin.getRegionContainer();
            RegionManager regions = container.get(drillPlace.getWorld());
            regions.addRegion(drillRegion);
            Main.getPlugin().activeDrills.add(player.getName());
            new thumperController(armorStand, player.getName(), 60, 0).start();
        } else {
            player.sendMessage(ChatColor.RED + "Здесь нельзя строить");
        }
    }

    private com.sk89q.worldedit.BlockVector convertToSk89qBV(Location location){
        return new com.sk89q.worldedit.BlockVector(location.getX(),location.getY(),location.getZ());
    }

}



package com.salo.thumper.listeners;

import com.salo.thumper.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by user on 13.01.2017.
 */
public class MoveItemInInventories implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Inventory inventory = e.getInventory();
        if(inventory.getTitle().equals("Добытые ресурсы") && e.getClickedInventory().getTitle().equals("container.inventory")){
            if(e.getCurrentItem().getType()==null)e.setCancelled(true);
            if(e.getCurrentItem().getType() != e.getCursor().getType()&&e.getCursor().getType()!=null)e.setCancelled(true);
            Main.getPlugin().getLogger().info(e.getCurrentItem().getType().toString()+":"+e.getCursor().getType());
        } else if(inventory.getTitle().equals("Добытые ресурсы") && e.getClickedInventory().getTitle().equals("Добытые ресурсы")){
            if(e.getAction().equals(InventoryAction.HOTBAR_SWAP)||e.getAction().equals(InventoryAction.COLLECT_TO_CURSOR))e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventroyClose(InventoryCloseEvent e){
        Inventory buffer = com.salo.thumper.Main.getPlugin().queuedRewards.get(e.getPlayer().getName());
        int shallRemove=1;
        for(ItemStack it : buffer.getContents()){
            if(it!=null)shallRemove=0;
        }
        if(shallRemove==1)com.salo.thumper.Main.getPlugin().queuedRewards.remove(e.getPlayer().getName());
    }

}

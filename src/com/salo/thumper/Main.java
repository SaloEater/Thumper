package com.salo.thumper;


import com.salo.thumper.commands.Thumper;
import com.salo.thumper.listeners.ClickEvent;
import com.salo.thumper.listeners.MoveItemInInventories;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 14.12.2016.
 */
public class Main extends JavaPlugin {

    private static Main plugin;
    public WorldGuardPlugin WEPlugin = WGBukkit.getPlugin();

    public Map<String, Inventory> queuedRewards = new HashMap<String, Inventory>();
    public List<String> activeDrills = new ArrayList<>();

    @Override
    public void onEnable() {
        plugin=this;
        this.getServer().getPluginManager().registerEvents(new ClickEvent(), this);
        this.getServer().getPluginManager().registerEvents(new MoveItemInInventories(), this);
        this.getCommand("th").setExecutor(new Thumper());
    }

    public Map<String, Inventory> getQueuedRewards() {
        return queuedRewards;
    }

    public List<String> getActiveDrills() {
        return activeDrills;
    }

    public void addActiveDrill(String playerName){
        activeDrills.add(playerName);
    }

    public void addRewardForPlayer(String playerName, Inventory inventory){
        queuedRewards.put(playerName, inventory);
    }

    public WorldGuardPlugin getWEPlugin() {
        return WEPlugin;
    }

    public static Main getPlugin() {
        return plugin;
    }

}

package com.salo.thumper;

import com.salo.thumper.commands.thumper;
import com.salo.thumper.listeners.clickEvent;
import com.salo.thumper.listeners.moveItemInInventories;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 14.12.2016.
 */
public class Main extends JavaPlugin {

    private static Main plugin;
    public WorldGuardPlugin WEPlugin = WGBukkit.getPlugin();

    public Map<String, Inventory> queuedRewards = new HashMap<String, Inventory>();


    @Override
    public void onEnable() {
        plugin=this;
        this.getServer().getPluginManager().registerEvents(new clickEvent(), this);
        this.getServer().getPluginManager().registerEvents(new moveItemInInventories(), this);
        this.getCommand("th").setExecutor(new thumper());
    }

    public static Main getPlugin() {
        return plugin;
    }

}

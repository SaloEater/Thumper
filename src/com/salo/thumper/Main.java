package com.salo.thumper;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import com.salo.thumper.commands.thumper;
import com.salo.thumper.listeners.clickEvent;

/**
 * Created by user on 14.12.2016.
 */
public class Main extends JavaPlugin {

    private static Main plugin;

    @Override
    public void onEnable() {
        plugin=this;
        this.getServer().getPluginManager().registerEvents(new clickEvent(), this);
        this.getCommand("th").setExecutor(new thumper());
    }

    public static Main getPlugin() {
        return plugin;
    }

}

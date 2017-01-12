package com.salo.thumper.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by user on 14.12.2016.
 */
public class thumper implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

            Player player = ((Player) commandSender).getPlayer();
            ItemStack is = new ItemStack(Material.ARMOR_STAND, 1);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName("Thumper");
            List<String> lore = new ArrayList<>();
            lore.add("Place to start");
            im.setLore(lore);
            is.setItemMeta(im);
            player.getInventory().addItem(is);

        return false;
    }
}

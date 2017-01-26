package com.salo.thumper.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 14.12.2016.
 */
public class thumper implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = ((Player) commandSender).getPlayer();
        if(args.length>0){
            switch(args[0]){
                case "get":
                    ItemStack is = new ItemStack(Material.ARMOR_STAND, 1);
                    ItemMeta im = is.getItemMeta();
                    im.setDisplayName("Thumper");
                    List<String> lore = new ArrayList<>();
                    lore.add("Place to start");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    player.getInventory().addItem(is);
                    break;

                case "receive":
                    if(com.salo.thumper.Main.getPlugin().queuedRewards.containsKey(player.getName())){
                        player.openInventory(com.salo.thumper.Main.getPlugin().queuedRewards.get(player.getName()));
                    } else {
                        player.sendMessage(ChatColor.RED + "Ваши закромы пустые!");
                    }
                    break;

                case "help":
                    player.sendMessage("Введите "+ChatColor.RED+ChatColor.BOLD+"/th get"+ChatColor.RESET+", чтобы получить бур");
                    player.sendMessage("Введите "+ChatColor.RED+ChatColor.BOLD+"/th receive"+ChatColor.RESET+", чтобы забрать ресурсы");
                    player.sendMessage("Введите "+ChatColor.RED+ChatColor.BOLD+"/th help"+ChatColor.RESET+", чтобы вывести это сообщение");
                    break;

            }
        }
        return false;
    }
}

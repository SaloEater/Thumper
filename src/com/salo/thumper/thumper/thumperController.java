package com.salo.thumper.thumper;

import com.salo.thumper.Main;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by user on 12.01.2017.
 */
public class thumperController extends Thread {

    private int liveTime;
    private int livedTime;
    private ArmorStand armorStand;
    private String thumperOwner;
    private Location drillPlace;
    private boolean shallStop=false;
    private Logger logger;

    public thumperController(ArmorStand armorStand, String thumperOwner, int liveTime, int livedTime){
        super(thumperOwner);
        this.logger=Main.getPlugin().getLogger();
        this.thumperOwner=thumperOwner;
        this.armorStand=armorStand;
        this.livedTime=livedTime;
        this.liveTime=liveTime;
    }

    public void run(){
        try {
            if(this.liveTime-this.livedTime!=0){
                this.armorStand.setCustomName(("§5Осталось: "+(liveTime-livedTime)));
            } else {
                this.armorStand.setCustomName(("§5Работа завершена"));
                Location asLocBuffer = armorStand.getLocation();

                Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        armorStand.remove();
                        asLocBuffer.getBlock().setType(Material.OBSIDIAN);
                        try {
                            RegionContainer container = Main.getPlugin().WEPlugin.getRegionContainer();
                            RegionManager regions = container.get(Main.getPlugin().getServer().getWorld("world"));
                            for(Player player : Main.getPlugin().getServer().getOnlinePlayers()){
                                logger.info(player.getName()+":"+regions.getApplicableRegions(player.getLocation()).getRegions().toString());
                                for(ProtectedRegion rg : regions.getApplicableRegions(player.getLocation()).getRegions()){
                                    if(rg.getId().equals("drillplace"+thumperOwner.toLowerCase())){
                                        Main.getPlugin().queuedRewards.put(player.getName(), createLoot());
                                    }
                                }
                            }
                            regions.removeRegion("drillPlace" + thumperOwner);
                        } catch (NullPointerException e) {
                            Main.getPlugin().getServer().getPlayer(thumperOwner).sendMessage(ChatColor.RED + "Проблема с регионами. Сообщите администрации, пожалуйста!");
                        }
                    }
                }, 100);
                shallStop=false;
                Main.getPlugin().activeDrills.remove(thumperOwner);
                Thread.currentThread().interrupt();
            }
            this.livedTime+=60;
            Thread.sleep(5000);
            if(!shallStop)new thumperController(this.armorStand, this.thumperOwner, this.liveTime, this.livedTime).start();
        } catch(InterruptedException e){
            if(shallStop)Main.getPlugin().getServer().getPlayer(thumperOwner).sendMessage(ChatColor.RED+"Плагин сломался. Сообщите администрации, пожалуйста!");
        }
    }

    private Inventory createLoot(){
        int amountFrom=48, amountAdded, itemsAmount, randNum, sameProc;
        int[] chances={35, 35, 15, 7, 5, 2, 1};
        Material[] ids = {Material.DIRT,
                          Material.COBBLESTONE,
                          Material.COAL_ORE,
                          Material.IRON_ORE,
                          Material.GOLD_ORE,
                          Material.DIAMOND_ORE,
                          Material.EMERALD_ORE};
        Random rnd = new Random();
        Inventory generated = Bukkit.createInventory(null, 9, "Добытые ресурсы");
        amountAdded=rnd.nextInt(16);
        itemsAmount=amountFrom+amountAdded;
        for(int i=0; i<itemsAmount; i++){
            randNum=rnd.nextInt(100);
            int sum=0;
            for(int j=0; j<chances.length; j++){
                sum+=chances[j];
                if(randNum<sum){
                    if(j==0||j==1){
                        sameProc=rnd.nextInt(2);
                        if(sameProc<1){
                            generated.addItem(new ItemStack(ids[0]));
                        } else {
                            generated.addItem(new ItemStack(ids[1]));
                        }
                    } else {
                        generated.addItem(new ItemStack(ids[j]));
                    }
                    break;
                }
            }
        }
        return generated;
    }

}

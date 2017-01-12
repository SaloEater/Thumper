package com.salo.thumper.thumper;

import com.salo.thumper.Main;
import com.salo.thumper.listeners.clickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.time.LocalDate;

/**
 * Created by user on 12.01.2017.
 */
public class thumperController extends Thread {

    private int liveTime;
    private int livedTime;
    private ArmorStand armorStand;
    private String thumperOwner;
    private Location drillPlace;

    public thumperController(ArmorStand armorStand, Location drillPlace, String thumperOwner, int liveTime, int livedTime){
        Main.getPlugin().getLogger().info("Controller");
        this.thumperOwner=thumperOwner;
        this.drillPlace=drillPlace;
        this.armorStand=armorStand;
        this.livedTime=livedTime;
        this.liveTime=liveTime;
    }

    public void run(){
        try {
            //Main.getPlugin().getLogger().info("Cycle");
            if(liveTime-livedTime!=0){
                armorStand.setCustomName(("§5Left: "+(liveTime-livedTime)));
            } else {
                this.stop();
            }
            livedTime+=5;
            Thread.sleep(5000);
            new thumperController(armorStand, drillPlace, thumperOwner, 60, 0).start();

        } catch(InterruptedException e){

        }
    }
}

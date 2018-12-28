package com.stilbruch.spigotcalc;

import org.bukkit.plugin.java.JavaPlugin;

public class SpigotCalcPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        //Register the main command
        this.getCommand("calc").setExecutor(new CalcCommand(this));

        //Register listeners
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
    }

}
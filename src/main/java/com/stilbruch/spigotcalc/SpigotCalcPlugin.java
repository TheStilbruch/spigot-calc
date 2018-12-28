package com.stilbruch.spigotcalc;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.bukkit.plugin.java.JavaPlugin;

public class SpigotCalcPlugin extends JavaPlugin {

    // I am going to be using a javascript engine to parse math expressions
    // While this solution would likely not be the best for a preformance sensitive
    // senario, it works well with the needs and time constraints of this project
    private final ScriptEngineManager factory = new ScriptEngineManager();
    public final ScriptEngine engine = factory.getEngineByName("JavaScript");

    @Override
    public void onEnable() {
        //Register the main command
        this.getCommand("calc").setExecutor(new CalcCommand(this));

        //Register listeners
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
    }

}
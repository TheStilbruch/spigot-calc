package com.stilbruch.spigotcalc;

import java.util.function.Consumer;

import com.stilbruch.spigotcalc.gui.ItemGui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        //We only care about when a CalcGui is clicked
        if (!(event.getWhoClicked() instanceof Player) 
            || event.getInventory() == null 
            || !(event.getInventory().getHolder() instanceof ItemGui)) {
            return;
        }

        ItemGui itemGui = (ItemGui) event.getInventory().getHolder();
        itemGui.registerClick(event);
    }

}
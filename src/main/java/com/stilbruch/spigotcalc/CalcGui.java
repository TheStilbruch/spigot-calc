package com.stilbruch.spigotcalc;

import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class CalcGui implements InventoryHolder {

    //Constants
    private static final int SIZE = 27;
    
    private Inventory inv;
    private Consumer<InventoryClickEvent>[] slotHandlers;

    public CalcGui() {
        inv = Bukkit.createInventory(this, SIZE);
        slotHandlers = new Consumer[SIZE];
    }

    /**
     * Displayes the calculator to a player
     */
    public void display(Player player) {
        player.openInventory(inv);
    }

    /**
     * This method handles when a player clicks the GUI. Keeping this inside the CalcGUI class keeps it's outward facing API clean
     */
    public void registerClick(final InventoryClickEvent clickEvent) {
        //Check if the slot is valid
        if (clickEvent.getSlot() < 0 || clickEvent.getSlot() > SIZE){
            return;
        }
        Consumer<InventoryClickEvent> clickHandler = slotHandlers[clickEvent.getSlot()];
        if (clickHandler != null) {
            clickHandler.accept(clickEvent);
        }
        clickEvent.setCancelled(true);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    private void fillRow(ItemStack item){
        
    }
}
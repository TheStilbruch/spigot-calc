package com.stilbruch.spigotcalc.gui;

import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemGui implements InventoryHolder {
    
    protected final int rows;
    protected final int size;
    protected final String title;
    private Inventory inv;
    private Consumer<InventoryClickEvent>[] slotHandlers;

    public ItemGui(int rows, String title) {
        this.rows = rows;
        this.size = rows * 9;
        this.title = title;
        inv = Bukkit.createInventory(this, size, title);
        slotHandlers = new Consumer[size];
    }

    /**
     * Constructor that copies an old GUI, but with a new title
     */
    public ItemGui(ItemGui gui, String title) {
        this.rows = gui.rows;
        this.size = gui.size;
        this.title = title;
        inv = Bukkit.createInventory(this, size, title);
        slotHandlers = gui.slotHandlers;

        //Populate the new inventory with all the old 
        for (int i = 0;i < size;i++) {
            inv.setItem(i, gui.inv.getItem(i));
        }
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
        if (clickEvent.getSlot() < 0 || clickEvent.getSlot() > size){
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

    /**
     * returns an item stack with a blank name, so that the name in the GUI is not the item name
     */
    protected ItemStack blankItem(Material material) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(" ");
        stack.setItemMeta(meta);
        return stack;
    }

    protected void setItem(int slot, ItemStack item, Consumer<InventoryClickEvent> clickHandler) {
        inv.setItem(slot, item);
        slotHandlers[slot] = clickHandler;
    }

    protected void fillColumn(int column, ItemStack item, Consumer<InventoryClickEvent> clickHandler){
        for (int i = 0; i < rows;i++) {
            setItem((i * 9) + column, item, clickHandler);
        }
    }

    protected void fillRow(int row, ItemStack item, Consumer<InventoryClickEvent> clickHander) {
        for (int i = 0;i < 9;i++) {
            setItem(row * 9 + i, item, clickHander);
        }
    }
}
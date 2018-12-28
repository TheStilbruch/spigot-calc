package com.stilbruch.spigotcalc.gui;

import java.util.function.Consumer;

import com.stilbruch.spigotcalc.SpigotCalcPlugin;
import com.stilbruch.spigotcalc.util.ItemUtils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CalcGui extends ItemGui {

    private final SpigotCalcPlugin plugin;

    public CalcGui(SpigotCalcPlugin plugin) {
        super(3, "Calculator: ");

        this.plugin = plugin;

        //Now the actually setup the calcualtor
        fillColumn(0, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);
        fillColumn(1, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);
        fillColumn(7, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);
        fillColumn(8, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);

        for (int i = 1;i < 10;i++) {
            int slot = ((i - 1) % 3) + ((i - 1) / 3) * 9 + 2; // Lot's of maths to get this in the right spot
            setItem(slot, getMenuItem(Material.EMERALD, String.valueOf(i)), getClickHandler(String.valueOf(i)));
        }

        setItem(5, getMenuItem(Material.REDSTONE, "+"), getClickHandler("+"));
    }

    private ItemStack getMenuItem(Material mat, String name) {
        return ItemUtils.addName(
            new ItemStack(mat), 
            ChatColor.GRAY + "[" + ChatColor.GREEN + name + ChatColor.GRAY + "]" //This is a bit messy
        );
    }

    private Consumer<InventoryClickEvent> getClickHandler(String name) {
        return (Consumer<InventoryClickEvent>) event -> {
            Player player = (Player) event.getWhoClicked();
            ItemGui gui = (ItemGui) event.getInventory().getHolder();

            player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_OFF, 1f, 1f);
            new ItemGui(gui, gui.title + name).display(player); //Basiclly changing the title of the GUI
        };
    }

}
package com.stilbruch.spigotcalc.gui;

import java.util.function.Consumer;

import javax.script.ScriptException;

import com.stilbruch.spigotcalc.SpigotCalcPlugin;
import com.stilbruch.spigotcalc.util.ItemUtils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.chat.ClickEvent;

public class CalcGui extends ItemGui {

    private static final String DEFAULT_TITLE = "Calculator: ";

    private final SpigotCalcPlugin plugin;

    public CalcGui(SpigotCalcPlugin plugin) {
        super(3, DEFAULT_TITLE);

        this.plugin = plugin;

        //Now the actually setup the calcualtor
        fillColumn(0, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);
        fillColumn(1, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);
        fillColumn(7, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);
        fillColumn(8, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);

        //Numbers 1-9
        for (int i = 1;i < 10;i++) {
            int slot = ((i - 1) % 3) + 2 + ((i - 1) / 3) * 9; // Lot's of maths to get this in the right spot
            setItem(slot, getMenuItem(Material.EMERALD, String.valueOf(i)), getClickHandler(String.valueOf(i)));
        }
        setItem(23, getMenuItem(Material.EMERALD, "0"), getClickHandler("0")); //Number 0

        //Add the operators
        setItem(5, getMenuItem(Material.REDSTONE, "+"), getClickHandler("+"));
        setItem(6, getMenuItem(Material.REDSTONE, "-"), getClickHandler("-"));
        setItem(14, getMenuItem(Material.REDSTONE, "*"), getClickHandler("*"));
        setItem(15, getMenuItem(Material.REDSTONE, "/"), getClickHandler("/"));

        //Clear button
        setItem(size - 1, getMenuItem(Material.BARRIER, ChatColor.RED + "CLEAR"), clickEvent -> {
            new CalcGui(plugin).display((Player) clickEvent.getWhoClicked());
        });

        //And finally the equals button
        setItem(24, getMenuItem(Material.REDSTONE, "="), clickEvent -> {
            ItemGui gui = (ItemGui) clickEvent.getInventory().getHolder();
            Player player = (Player) clickEvent.getWhoClicked();

            try {
                Object rawSolution = plugin.engine.eval(gui.title.replace(DEFAULT_TITLE, ""));
                double solution = Double.parseDouble(rawSolution.toString());

                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2f, 1f);
                new ItemGui(gui, DEFAULT_TITLE + String.valueOf(solution)).display(player); //Change the title to have the solution
            } catch (ScriptException e) {
                //TODO: Handler
                e.printStackTrace();
            }
            
        });
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
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

public class CalcGui extends ItemGui {

    private static final String DEFAULT_TITLE = "Calculator: ";
    private static final Material NUMBER_MATERIAL = Material.LIME_DYE;
    private static final Material OPERATOR_MATERIAL = Material.GRAY_DYE;

    private final SpigotCalcPlugin plugin;

    //If i had more time i would remove all the magic values in this function
    public CalcGui(SpigotCalcPlugin plugin) {
        super(5, DEFAULT_TITLE);

        this.plugin = plugin;

        //Now the actually setup the calcualtor
        fillColumn(0, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);
        fillColumn(1, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);
        fillColumn(7, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);
        fillColumn(8, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);
        fillRow(0, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);
        fillRow(4, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);

        //Numbers 1-9
        for (int i = 1;i < 10;i++) {
            int slot = ((i - 1) % 3) + 2 + (((i - 1) / 3) + 1) * 9; // Lot's of maths to get this in the right spot
            setItem(slot, getMenuItem(NUMBER_MATERIAL, String.valueOf(i)), getClickHandler(String.valueOf(i)));
        }
        setItem(32, getMenuItem(NUMBER_MATERIAL, "0"), getClickHandler("0")); //Number 0

        //Add the operators
        setItem(14, getMenuItem(OPERATOR_MATERIAL, "+"), getClickHandler("+"));
        setItem(15, getMenuItem(OPERATOR_MATERIAL, "-"), getClickHandler("-"));
        setItem(23, getMenuItem(OPERATOR_MATERIAL, "*"), getClickHandler("*"));
        setItem(24, getMenuItem(OPERATOR_MATERIAL, "/"), getClickHandler("/"));

        //Clear button
        setItem(size - 1, getMenuItem(Material.BARRIER, ChatColor.RED + "CLEAR"), clickEvent -> {
            Player player = (Player) clickEvent.getWhoClicked();

            player.playSound(player.getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1f, 1f);
            new CalcGui(plugin).display((Player) clickEvent.getWhoClicked());
        });

        //And finally the equals button
        setItem(33, getMenuItem(Material.GOLD_NUGGET, "="), clickEvent -> {
            ItemGui gui = (ItemGui) clickEvent.getInventory().getHolder();
            Player player = (Player) clickEvent.getWhoClicked();

            try {
                Object rawSolution = plugin.engine.eval(gui.title.replace(DEFAULT_TITLE, ""));
                float solution = Float.parseFloat(rawSolution.toString());

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                new ItemGui(gui, DEFAULT_TITLE + String.valueOf(solution)).display(player); //Change the title to have the solution
            } catch (ScriptException e) {
                plugin.sendError(player, "Error parsing equation!");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
                player.closeInventory();
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
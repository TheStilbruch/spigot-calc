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
import org.bukkit.inventory.meta.ItemMeta;

public class CalcGui extends ItemGui {

    public CalcGui() {
        //Now the actually setup the calcualtor
        fillColumn(0, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);
        fillColumn(1, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);
        fillColumn(7, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);
        fillColumn(8, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);
    }

}
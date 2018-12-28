package com.stilbruch.spigotcalc.gui;

import com.stilbruch.spigotcalc.util.ItemUtils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CalcGui extends ItemGui {

    public CalcGui() {
        super(3);

        //Now the actually setup the calcualtor
        fillColumn(0, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);
        fillColumn(1, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);
        fillColumn(7, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);
        fillColumn(8, blankItem(Material.BLACK_STAINED_GLASS_PANE), null);

        //Now the numbers
        //1 - 2
        //2 - 3
        //3 - 4
        //4 - 11
        for (int i = 1;i < 10;i++) {
            int slot = ((i - 1) % 3) + ((i - 1) / 3) * 9 + 2; // Lot's of maths to get this in the right spot
            setItem(slot, getNumberItem(i), null);
        }
    }

    private ItemStack getNumberItem(int number) {
        return ItemUtils.addName(new ItemStack(Material.EMERALD), String.valueOf(number));
    }

}
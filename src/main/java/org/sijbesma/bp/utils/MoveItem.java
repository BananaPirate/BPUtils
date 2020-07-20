package org.sijbesma.bp.utils;

import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class MoveItem {

    public static void cursorToSlot(Player player, Inventory inventory, int slot) {
        ItemStack itemOnCursor = player.getItemOnCursor().clone();
        ItemStack itemInSlot = inventory.getItem(slot).clone();

        if (itemOnCursor.isSimilar(itemInSlot)) {
            // add items from cursor to stack
            addItemFromCursor(player, inventory, slot);
        } else {
            // swap item and cursor
            swapItemFromCursor(player, inventory, slot);
        }
    }

    public static void itemStacktoSlot(ItemStack item, Inventory inventory, int slot) {
        inventory.setItem(slot, item.clone());
    }

    public static boolean slotToSlot(int sourceSlot, int targetSlot, InventoryView inventoryView) {


        ItemStack sourceStack = inventoryView.getItem(sourceSlot);
        ItemStack targetStack = inventoryView.getItem(targetSlot);
        if (sourceStack == null || sourceStack.getType().isAir()) {
            //cannot move nothing
            return false;
        }
        if (targetStack == null || targetStack.getType().isAir()) {
            // target stack is empty
            inventoryView.setItem(targetSlot, sourceStack);
            inventoryView.setItem(sourceSlot, new ItemStack(Material.AIR));
            return true;
        }

        int sourceAmount = sourceStack.getAmount();
        int targetAmount = targetStack.getAmount();
        int targetMax = targetStack.getMaxStackSize();
        int targetSpace = targetMax - targetAmount;

        if (sourceStack.isSimilar(targetStack)) {
            if (targetSpace >= sourceAmount) {
                sourceStack.setAmount(0);
                targetStack.setAmount(targetAmount + sourceAmount);
                return true;
            } else if(targetSpace > 0) {
                sourceStack.setAmount(sourceAmount - targetSpace);
                targetStack.setAmount(targetMax);
                return true;
            }

        }
        //items do not match so do nothing
        return false;
    }

    private static void addItemFromCursor(Player player, Inventory inventory, int slot) {
        ItemStack itemOnCursor = player.getItemOnCursor().clone();
        ItemStack itemInSlot = inventory.getItem(slot).clone();
        int cursorAmount = itemOnCursor.getAmount();
        int slotAmount = itemInSlot.getAmount();
        int spaceInSlot = itemInSlot.getMaxStackSize() - itemInSlot.getAmount();
        if (spaceInSlot > 0) {
            if (spaceInSlot >= cursorAmount) {
                itemInSlot.setAmount(slotAmount + cursorAmount);
                itemOnCursor.setAmount(0);
            } else {
                itemInSlot.setAmount(slotAmount + spaceInSlot);
                itemOnCursor.setAmount(cursorAmount - spaceInSlot);
            }
        }
        player.setItemOnCursor(itemOnCursor);
        inventory.setItem(slot, itemInSlot);
    }

    private static void swapItemFromCursor(Player player, Inventory inventory, int slot) {
        ItemStack itemOnCursor = player.getItemOnCursor().clone();
        ItemStack itemInSlot = inventory.getItem(slot).clone();
        player.setItemOnCursor(itemInSlot);
        inventory.setItem(slot, itemOnCursor);
    }
}

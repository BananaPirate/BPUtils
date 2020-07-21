package org.sijbesma.bp.utils;

import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class MoveItem {

    public static boolean cursorToSlot(Player player, InventoryView inventoryView, int slot) {
        System.out.println("test");
        ItemStack itemOnCursor = player.getItemOnCursor();
        if(itemOnCursor == null){
            return false;
        }

        ItemStack itemInSlot = inventoryView.getItem(slot);
        if(itemInSlot == null){
            itemInSlot = new ItemStack(Material.AIR,0);
        }
        

        if (itemOnCursor.isSimilar(itemInSlot)) {
            // add items from cursor to stack
            return addItemFromCursor(player, inventoryView, slot);
        } else {
            // swap item and cursor
            return swapItemFromCursor(player, inventoryView, slot);
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
            inventoryView.setItem(sourceSlot, new ItemStack(Material.AIR,0));
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

    private static boolean addItemFromCursor(Player player, InventoryView inventoryView, int slot) {
        ItemStack itemOnCursor = player.getItemOnCursor();
        if (itemOnCursor == null){
            return false;
        } else {
            itemOnCursor = itemOnCursor.clone();
        }
        ItemStack itemInSlot = inventoryView.getItem(slot);
        if(itemInSlot == null){
            itemInSlot = new ItemStack(Material.AIR,0);
        }else{
            itemInSlot = itemInSlot.clone();
        }
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
        }else{
            return false;
        }
        player.setItemOnCursor(itemOnCursor);
        inventoryView.setItem(slot, itemInSlot);
        return true;
    }

    private static boolean swapItemFromCursor(Player player, InventoryView inventoryView, int slot) {
        ItemStack itemOnCursor = player.getItemOnCursor();
        if (itemOnCursor == null){
            return false;
        } else {
            itemOnCursor = itemOnCursor.clone();
        }
        ItemStack itemInSlot = inventoryView.getItem(slot);
        if(itemInSlot == null){
            itemInSlot = new ItemStack(Material.AIR,0);
        }else{
            itemInSlot = itemInSlot.clone();
        }
        player.setItemOnCursor(itemInSlot);
        inventoryView.setItem(slot, itemOnCursor);
        return true;
    }
}

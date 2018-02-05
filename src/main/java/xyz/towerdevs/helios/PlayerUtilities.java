package xyz.towerdevs.helios;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import xyz.towerdevs.helios.instantiable.InventoryItem;

public class PlayerUtilities {
	public static boolean playerHasItem(EntityPlayer player, Item item) {
		if (player.capabilities.isCreativeMode)
			return true;
		
		if (getPlayerItem(player, item) != null)
			return true;
		
		return false;
	}
	
	public static void removePlayerItem(EntityPlayer player, Item item, int count) {
		InventoryItem pitem = getPlayerItem(player, item);
		if (pitem == null)
			return;
		
		player.inventory.decrStackSize(pitem.getSlot(), count);
	}
	
	public static void damageItem(EntityPlayer player, Item item, int damage) {
		if (player.capabilities.isCreativeMode)
			return;
		
		InventoryItem pitem = getPlayerItem(player, item);
		if (pitem == null)
			return;
		
		ItemStack newItem = pitem.getItem();
		
	    newItem.setItemDamage(newItem.getItemDamage() + damage);
	    player.inventory.setInventorySlotContents(pitem.getSlot(), newItem);
	}
	
	public static InventoryItem getPlayerItem(EntityPlayer player, Item item, int itemDamage) {
		ItemStack[] inventory = player.inventory.mainInventory;
		for (int i = 0; i < inventory.length; i++) {
			ItemStack it = inventory[i];
			if (it != null && it.getItem() == item && it.getItemDamage() == itemDamage) {
				return new InventoryItem(it, i);
			}
		}
		
		return null;
	}
	
	public static InventoryItem getPlayerItem(EntityPlayer player, Item item) {
		ItemStack[] inventory = player.inventory.mainInventory;
		for (int i = 0; i < inventory.length; i++) {
			ItemStack it = inventory[i];
			if (it != null && it.getItem() == item) {
				return new InventoryItem(it, i);
			}
		}
		
		return null;
	}
}

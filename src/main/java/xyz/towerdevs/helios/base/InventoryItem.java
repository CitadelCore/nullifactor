package xyz.towerdevs.helios.base;

import net.minecraft.item.ItemStack;

public class InventoryItem {
	ItemStack item;
	int slot;
	
	public InventoryItem(ItemStack item, int slot) {
		this.item = item;
		this.slot = slot;
	}
	
	public ItemStack getItem() { return item; }
	public int getSlot() { return slot; }
}

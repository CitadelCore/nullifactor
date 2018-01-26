package xyz.towerdevs.helios.base;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotFiltered extends Slot {
	private Class<? extends Item> filteredItem;
	
	public SlotFiltered(IInventory player, int index, int x, int y, Class<? extends Item> filteredItem) {
		super(player, index, x, y);
		
		this.filteredItem = filteredItem;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		if (stack.getItem().getClass() == this.filteredItem)
			return true;
		
		return false;
	}
}

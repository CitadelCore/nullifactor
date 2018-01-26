package xyz.towerdevs.helios.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public abstract class HeliosContainer extends Container {
	protected IInventory inventory;
	protected TileEntity entity;
	public HeliosContainer(IInventory inventory, TileEntity entity) {
		this.inventory = inventory;
		this.entity = entity;
	}
	
	protected void addPlayerInventorySlots() {
		for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(this.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(this.inventory, i, 8 + i * 18, 142));
        }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.inventory.isUseableByPlayer(player);
	}
}

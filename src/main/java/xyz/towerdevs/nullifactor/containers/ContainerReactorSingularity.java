package xyz.towerdevs.nullifactor.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import xyz.towerdevs.helios.base.HeliosContainer;
import xyz.towerdevs.helios.base.SlotFiltered;
import xyz.towerdevs.nullifactor.items.ItemSingularity;

public class ContainerReactorSingularity extends HeliosContainer {
	//TileEntityQuantumReactorSingularity core;
	public ContainerReactorSingularity(IInventory inventory, TileEntity entity) {
		super(inventory, entity);
		//this.core = (TileEntityQuantumReactorSingularity) core;
		
		this.addPlayerInventorySlots();
		this.addSlotToContainer(new SlotFiltered(this.inventory, 0, 80, 23, ItemSingularity.class));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
		return null;
	}
}
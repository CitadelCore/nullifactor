package xyz.towerdevs.nullifactor.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import xyz.towerdevs.helios.base.HeliosContainer;

public class ContainerReactorController extends HeliosContainer {
	public ContainerReactorController(IInventory inventory, TileEntity entity) {
		super(inventory, entity);
		this.addPlayerInventorySlots();
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
		return null;
	}

	@Override
	public void recievePacket(int index, int value) {
	}
}

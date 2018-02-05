package xyz.towerdevs.nullifactor.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import xyz.towerdevs.helios.base.HeliosContainer;
import xyz.towerdevs.helios.base.SlotFiltered;
import xyz.towerdevs.nullifactor.common.items.ItemSingularity;

public class ContainerReactorSingularity extends HeliosContainer {
	public ContainerReactorSingularity(IInventory inventory, TileEntity entity) {
		super(inventory, entity);
		this.addSlotToContainer(new SlotFiltered((IInventory) this.entity, 0, 80, 23, ItemSingularity.class));
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
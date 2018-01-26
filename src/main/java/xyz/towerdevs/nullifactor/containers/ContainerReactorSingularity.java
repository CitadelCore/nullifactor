package xyz.towerdevs.nullifactor.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import xyz.towerdevs.helios.base.SlotFiltered;
import xyz.towerdevs.nullifactor.items.ResourceItemRegistry;
import xyz.towerdevs.nullifactor.tileentities.TileEntityQuantumReactorSingularity;

public class ContainerReactorSingularity extends Container {
	InventoryPlayer player;
	TileEntityQuantumReactorSingularity core;
	public ContainerReactorSingularity(InventoryPlayer player, TileEntityQuantumReactorSingularity core) {
		this.player = player;
		this.core = core;
		
		this.addSlotToContainer(new SlotFiltered(core, 0, 80, 23, ResourceItemRegistry.SINGULBEDROCK.getItem().getClass()));
		
		for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.core.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
		return null;
	}
}
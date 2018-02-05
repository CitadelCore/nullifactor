package xyz.towerdevs.helios.base;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import xyz.towerdevs.helios.HeliosMod;
import xyz.towerdevs.helios.network.NetworkDataType;
import xyz.towerdevs.helios.network.packets.TileDataPacket;

public abstract class HeliosContainer extends Container {
	protected IInventory inventory;
	protected TileEntity entity;
	private int nextSlotId = 42; // Leave room for player inventory slots.
	public HeliosContainer(IInventory inventory, TileEntity entity) {
		this.inventory = inventory;
		this.entity = entity;
	}
	
	/** This is called when this container recieves a packet. 
	 * It can be called on both sides, client and server. */
	public abstract void recievePacket(int index, int value);
	
	/** Updates all clients who are registered for notification on this TileEntity that 
	 * a variable on the server side has changed which needs to be refreshed.*/
	public Object updateClients(HeliosTileEntity tileEntity, HeliosMod mod, int index, Object object) {
		for (Object player : this.crafters)
			mod.networkWrapper.sendTo(new TileDataPacket(tileEntity, NetworkDataType.TYPE_INT, index, object), (EntityPlayerMP) player);
		
		return object;
	}
	
	/** Updates the server from the client side that a variable has changed on the client side
	 * which needs to be sent to the server. Security risk here. */
	@SideOnly(Side.CLIENT)
	public Object updateServer(HeliosTileEntity tileEntity, HeliosMod mod, int index, Object object) {
		mod.networkWrapper.sendToServer(new TileDataPacket(tileEntity, NetworkDataType.TYPE_INT, index, object));
		return object;
	}
	
	/** Adds player slots to a container. Should add 36 slots in total. */
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
	
	/** Retrieves the next available slot ID. */
	protected int getNextSlotId() {
		int id = this.nextSlotId;
		this.nextSlotId++;
		return id;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.inventory.isUseableByPlayer(player);
	}
}

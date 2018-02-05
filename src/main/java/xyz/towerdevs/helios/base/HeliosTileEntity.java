package xyz.towerdevs.helios.base;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import xyz.towerdevs.helios.network.packets.TileDataPacket;
import xyz.towerdevs.nullifactor.Nullifactor;

public class HeliosTileEntity extends TileEntity {
	/** Whether the entity should update. */
	protected boolean canUpdate = false;
	
	/** Number of ticks since the entity was spawned.*/
	protected int currentTick;
	
	@Override
	public boolean canUpdate() {
		return this.canUpdate;
	}
	
	@Override
	public void updateEntity() {
		// Should increment the tick counter.
		this.currentTick++;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.hookReadNBT(tag);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		this.hookWriteNBT(tag);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		this.hookReadNBT(pkt.func_148857_g());
	}
	
	/** Returns the description of this block. */
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.hookWriteNBT(tag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, -999, tag);
	}
	
	/** Updates the clients at the TargetPoint with a tile entity data update packet. */
	public Object updateClients(int index, int packetType, Object obj, TargetPoint target) {
		Nullifactor.instance.networkWrapper.sendToAllAround(new TileDataPacket(this, index, packetType, obj), target);
		return obj;
	}
	
	/** Updates all clients in the specified block radius with a data update packet. */
	public Object updateClients(int index, int packetType, Object obj, int radius) {
		if (this.worldObj == null || this.worldObj.provider == null) {
			Nullifactor.instance.logger.error("Attempted to send a client update packet, but the worldObj was null! Contact a developer.");
			return null;
		}
		
		return this.updateClients(index, packetType, obj, new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, radius));
	}
	
	/** Updates all clients in a 64-block radius with a new data update packet. */
	public Object updateClients(int index, int i, Object obj) {
		return this.updateClients(index, i, obj, 64);
	}
	
	/** Updates the server with data from the client. 
	 * This must be security checked before it is allowed to set any variables! */
	@SideOnly(Side.CLIENT)
	public Object updateServer(int index, byte packetType, Object obj) {
		Nullifactor.instance.networkWrapper.sendToServer(new TileDataPacket(this, index, packetType, obj));
		return obj;
	}
	
	/** Called when the client receives a packet from this server with a variable update for this tile entity. */
	@SideOnly(Side.CLIENT)
	public void recieveServerPacket(int index, Object obj) {}
	
	/** Called when the server receives a packet from the client with a variable update for this tile entity. */
	public void recieveClientPacket(int index, Object obj) {}
	
	public void hookReadNBT(NBTTagCompound tag) {}
	public void hookWriteNBT(NBTTagCompound tag) {}
}

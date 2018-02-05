package xyz.towerdevs.nullifactor.common.tileentities;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import xyz.towerdevs.helios.network.NetworkDataType;

public class TileEntityQuantumReactorController extends TileEntityQuantumBase implements IEnergyReceiver {
	protected static int maxCapacity = 1000000;
	protected int energySent;
	
	protected EnergyStorage storage = new EnergyStorage(maxCapacity);
	
	@Override
	public void hookReadNBT(NBTTagCompound nbt) {
		super.hookReadNBT(nbt);
		this.storage.readFromNBT(nbt);
	}
	
    @Override
	public void hookWriteNBT(NBTTagCompound nbt) {
		super.hookWriteNBT(nbt);
		this.storage.writeToNBT(nbt);
	}
    
    @Override
    public void updateEntity() {
    	super.updateEntity();
    	
    	if (!this.worldObj.isRemote) {
    		if (this.currentTick % 400 == 0) {
    			this.sendTileEntityToClient(true);
    		} else {
    			this.sendTileEntityToClient(false);
    		}
    	}
    		
    }
   
    public void sendTileEntityToClient(boolean force) {
    	if (this.energySent != this.storage.getEnergyStored() || force)
    		this.energySent = (Integer) this.updateClients(NetworkDataType.TYPE_INT, 0, this.storage.getEnergyStored());
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void recieveServerPacket(int index, Object object) {
		if (index == 0)
			this.storage.setEnergyStored((Integer) object);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection direction) {
		return true;
	}

	@Override
	public int getEnergyStored(ForgeDirection direction) {
		return this.storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection direction) {
		return this.storage.getMaxEnergyStored();
	}

	@Override
	public int receiveEnergy(ForgeDirection direction, int maxRecieve, boolean simulate) {
		return this.storage.receiveEnergy(maxRecieve, simulate);
	}
}

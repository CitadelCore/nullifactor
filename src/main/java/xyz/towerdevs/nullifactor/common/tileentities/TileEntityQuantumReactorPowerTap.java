package xyz.towerdevs.nullifactor.common.tileentities;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityQuantumReactorPowerTap extends TileEntityQuantumBase implements IEnergyReceiver, IEnergyProvider {
	protected static int maxCapacity = 100000000;
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
    
    /** Adds the specified amount of RPU (Reactor Power Units) to the power tap. They are converted to RF/MJ/J/EU/SP instantly. */
    public int recievePowerUnits(double powerPerTap) {
    	// TODO: Power conversion for RC shaft power?
    	// 1 RPU = 1kRF / 100MJ / 2500J / 250EU
    	
    	return this.storage.receiveEnergy((int) powerPerTap * 1000, false);
    }
	
	@Override
	public int extractEnergy(ForgeDirection direction, int maxExtract, boolean simulate) {
		if (ForgeDirection.getOrientation(getFacing()) == direction)
			return this.storage.extractEnergy(maxExtract, simulate);
		
		return 0;
	}

	@Override
	public int receiveEnergy(ForgeDirection direction, int maxRecieve, boolean simulate) {
		return this.storage.receiveEnergy(maxRecieve, simulate);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection direction) {
		if (ForgeDirection.getOrientation(getFacing()) == direction)
			return true;
		
		return false;
	}

	@Override
	public int getEnergyStored(ForgeDirection direction) {
		return this.storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection direction) {
		return this.storage.getMaxEnergyStored();
	}
}

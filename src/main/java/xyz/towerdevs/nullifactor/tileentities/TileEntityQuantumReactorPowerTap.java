package xyz.towerdevs.nullifactor.tileentities;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityQuantumReactorPowerTap extends TileEntityQuantumBase implements IEnergyStorage, IEnergyProvider {
	protected static int maxCapacity = 100000000;
	protected static int maxExtract = 10000000;
	protected int energy;
	
	@Override
	public void hookReadNBT(NBTTagCompound nbt) {
		super.hookReadNBT(nbt);
		
		this.energy = nbt.getInteger("Energy");
		
		if (this.energy > TileEntityQuantumReactorPowerTap.maxCapacity)
			this.energy = TileEntityQuantumReactorPowerTap.maxCapacity;
	}
	
    @Override
	public void hookWriteNBT(NBTTagCompound nbt) {
		super.hookWriteNBT(nbt);
		
		if (this.energy < 0)
			this.energy = 0;
		
		nbt.setInteger("Energy", this.energy);
	}
    
    /** Adds the specified amount of RPU (Reactor Power Units) to the power tap. They are converted to RF/MJ/J/EU/SP instantly. */
    public void recievePowerUnits(int reactorPowerUnits) {
    	// TODO: Power conversion for RC shaft power?
    	// 1 RPU = 1kRF / 100MJ / 2500J / 250EU
    	this.energy += reactorPowerUnits * 1000;
    }
	
	@Override
	public int extractEnergy(int maxExtract, boolean simulate)
	{
		int energyExtracted = Math.min(this.energy, Math.min(TileEntityQuantumReactorPowerTap.maxExtract, maxExtract));
		
		if (!simulate)
			this.energy -= energyExtracted;
		
		return energyExtracted;
	}

	@Override
	public int getEnergyStored() {
		return this.energy;
	}

	@Override
	public int getMaxEnergyStored() {
		return TileEntityQuantumReactorPowerTap.maxCapacity;
	}

	@Override
	public int receiveEnergy(int maxRecieve, boolean simulate) {
		// Don't do anything here, as there's no point in the power tap recieving RF...
		return 0;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection direction) {
		if (ForgeDirection.getOrientation(getFacing()) == direction)
			return true;
		
		return false;
	}

	@Override
	public int extractEnergy(ForgeDirection direction, int maxExtract, boolean simulate) {
		return this.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection direction) {
		return this.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection direction) {
		return this.getMaxEnergyStored();
	}

}

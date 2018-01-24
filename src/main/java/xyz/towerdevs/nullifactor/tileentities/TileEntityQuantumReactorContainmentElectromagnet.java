package xyz.towerdevs.nullifactor.tileentities;

import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.IEnergyStorage;
import net.minecraftforge.common.util.ForgeDirection;
import xyz.towerdevs.helios.base.HeliosMachineTileEntity;

public class TileEntityQuantumReactorContainmentElectromagnet extends HeliosMachineTileEntity implements IEnergyStorage, IEnergyReceiver {

	@Override
	public boolean canConnectEnergy(ForgeDirection direction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getEnergyStored(ForgeDirection direction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection direction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int receiveEnergy(ForgeDirection direction, int maxEnergy, boolean simulate) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int extractEnergy(int maxEnergy, boolean simulate) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEnergyStored() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxEnergyStored() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int receiveEnergy(int maxEnergy, boolean simulate) {
		// TODO Auto-generated method stub
		return 0;
	}
}

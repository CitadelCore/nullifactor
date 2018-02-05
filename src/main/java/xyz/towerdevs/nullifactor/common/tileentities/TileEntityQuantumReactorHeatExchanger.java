package xyz.towerdevs.nullifactor.common.tileentities;

import java.util.List;

import xyz.towerdevs.helios.instantiable.TileEntityReference;

public class TileEntityQuantumReactorHeatExchanger extends TileEntityQuantumBase {
	public TileEntityQuantumReactorHeatExchanger() {
		// Slightly higher max temperature
		this.canUpdate = true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (!this.worldObj.isRemote) {
			this.convertHeatToRPU();
		}
	}
	
	@Override
	public void addTemperature(double temp) {
		super.addTemperature(temp);
		
		// Converts heat to RPU instantly to avoid heat dissipation
		this.convertHeatToRPU();
	}
	
	// 1C/t = 1RPU/t = 1kRF/t
	// Example: Bedrock Singularity = 100C/t = 100RPU/t = 100kRF/t
	
	/** Converts heat to RPU (reactor power units). */
	private void convertHeatToRPU() {
		if (!this.hasMaster() || this.casingTemperature <= 0)
			return;
		
		TileEntityReference ref = this.getMaster();
		if (ref.refTileEntity != null && ref.refTileEntity instanceof TileEntityQuantumReactor) {
			TileEntityQuantumReactor core = (TileEntityQuantumReactor) ref.refTileEntity;
			
			List<TileEntityQuantumBase> powerTaps = core.getPowerTaps();
			if (powerTaps == null || powerTaps.size() <= 0)
				return;
			
			double powerPerTap = this.casingTemperature / powerTaps.size();
			for (int i = 0; i < powerTaps.size(); i++) {
				TileEntityQuantumReactorPowerTap tap = (TileEntityQuantumReactorPowerTap) powerTaps.get(i);
				tap.recievePowerUnits(powerPerTap);
			}
		}
	}
}

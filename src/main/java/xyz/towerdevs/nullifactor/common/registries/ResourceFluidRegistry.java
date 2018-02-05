package xyz.towerdevs.nullifactor.common.registries;

import xyz.towerdevs.helios.base.HeliosFluid;

public enum ResourceFluidRegistry {
	QUANTUMFUEL("liquid_quantum_fuel", "Liquid Quantum Fuel", 0xE2E2E2),
	DEPQUANTUMFUEL("liquid_depleted_quantum_fuel", "Depleted Quantum Fuel", 0xE2E2E2),
	LIQUIDHELIUM("liquid_helium", "Liquid Helium-3", 0xCCE2E5),
	;
	
	public static final ResourceFluidRegistry[] fluidRegistry = values();
	private HeliosFluid fluid;
	
	private ResourceFluidRegistry(String fluidName, String localizedName, int colour) {
		this.fluid = new HeliosFluid(fluidName, localizedName);
		this.fluid.setColour(colour);
	}
	
	private ResourceFluidRegistry(String fluidName, int colour) {
		this(fluidName, null, colour);
	}
	
	public HeliosFluid getFluid() {
		return this.fluid;
	}
}

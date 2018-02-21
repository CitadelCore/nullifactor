package xyz.towerdevs.nullifactor.common.blocks.reactor;

import net.minecraft.block.material.Material;
import xyz.towerdevs.nullifactor.Nullifactor;
import xyz.towerdevs.nullifactor.common.tileentities.TileEntityQuantumReactorFluidPort;

public class BlockQuantumReactorFluidPort extends BlockQuantumBase {
	public static final BlockQuantumReactorFluidPort instance = new BlockQuantumReactorFluidPort();
	public BlockQuantumReactorFluidPort() {
		super("quantum_reactor_fluid_port", Material.iron, "nullifactor", TileEntityQuantumReactorFluidPort.class);
        this.setMultiSidedTexture("nullifactor:quantum_reactor_pylon_side", "nullifactor:quantum_reactor_fuel_intake", "nullifactor:quantum_reactor_pylon_top", "nullifactor:quantum_reactor_pylon_top");
		
		this.setCreativeTab(Nullifactor.resourceCreativeTab);
	}
}

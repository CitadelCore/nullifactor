package xyz.towerdevs.nullifactor.blocks;

import net.minecraft.block.material.Material;
import xyz.towerdevs.helios.base.HeliosBlock;
import xyz.towerdevs.nullifactor.Nullifactor;

public class BlockQuantumReactorPylonCap extends HeliosBlock {
	public static final BlockQuantumReactorPylonCap instance = new BlockQuantumReactorPylonCap();
	public BlockQuantumReactorPylonCap() {
		super("quantum_reactor_pylon_cap", Material.iron, "nullifactor");
        this.setMultiSidedTexture("nullifactor:base_machine_frame", "nullifactor:base_machine_frame", "nullifactor:base_machine_frame", "nullifactor:quantum_reactor_pylon_top");
		
		this.setCreativeTab(Nullifactor.resourceCreativeTab);
	}
}

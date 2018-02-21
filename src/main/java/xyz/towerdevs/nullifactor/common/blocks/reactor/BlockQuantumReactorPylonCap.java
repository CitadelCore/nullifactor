package xyz.towerdevs.nullifactor.common.blocks.reactor;

import net.minecraft.block.material.Material;
import xyz.towerdevs.nullifactor.Nullifactor;
import xyz.towerdevs.nullifactor.common.tileentities.TileEntityQuantumBase;

public class BlockQuantumReactorPylonCap extends BlockQuantumBase {
	public static final BlockQuantumReactorPylonCap instance = new BlockQuantumReactorPylonCap();
	public BlockQuantumReactorPylonCap() {
		super("quantum_reactor_pylon_cap", Material.iron, "nullifactor", TileEntityQuantumBase.class);
        this.setMultiSidedTexture("nullifactor:base_machine_frame", "nullifactor:base_machine_frame", "nullifactor:base_machine_frame", "nullifactor:quantum_reactor_pylon_top");
		
		this.setCreativeTab(Nullifactor.resourceCreativeTab);
	}
}

package xyz.towerdevs.nullifactor.common.blocks;

import net.minecraft.block.material.Material;
import xyz.towerdevs.nullifactor.Nullifactor;
import xyz.towerdevs.nullifactor.common.tileentities.TileEntityQuantumReactorFailsafe;

public class BlockQuantumReactorFailsafe extends BlockQuantumBase {
	public static final BlockQuantumReactorFailsafe instance = new BlockQuantumReactorFailsafe();
	public BlockQuantumReactorFailsafe() {
		super("quantum_reactor_failsafe", Material.iron, "nullifactor", TileEntityQuantumReactorFailsafe.class);
        this.setMultiSidedTexture("nullifactor:quantum_reactor_pylon_side", "nullifactor:quantum_reactor_failsafe", "nullifactor:quantum_reactor_pylon_top", "nullifactor:quantum_reactor_pylon_top");
		
		this.setCreativeTab(Nullifactor.resourceCreativeTab);
	}
}

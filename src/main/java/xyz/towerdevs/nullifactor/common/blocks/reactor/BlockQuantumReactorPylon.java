package xyz.towerdevs.nullifactor.common.blocks.reactor;

import net.minecraft.block.material.Material;
import xyz.towerdevs.nullifactor.Nullifactor;
import xyz.towerdevs.nullifactor.common.tileentities.TileEntityQuantumReactorPylon;

public class BlockQuantumReactorPylon extends BlockQuantumBase {
	public static final BlockQuantumReactorPylon instance = new BlockQuantumReactorPylon();
	public BlockQuantumReactorPylon() {
		super("quantum_reactor_pylon", Material.iron, "nullifactor", TileEntityQuantumReactorPylon.class);
        this.setMultiSidedTexture("nullifactor:quantum_reactor_pylon_side", "nullifactor:quantum_reactor_pylon_side", "nullifactor:quantum_reactor_pylon_top", "nullifactor:quantum_reactor_pylon_top");
		
		this.setCreativeTab(Nullifactor.resourceCreativeTab);
	}
}

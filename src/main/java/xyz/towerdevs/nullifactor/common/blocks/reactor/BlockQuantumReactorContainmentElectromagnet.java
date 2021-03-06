package xyz.towerdevs.nullifactor.common.blocks.reactor;

import net.minecraft.block.material.Material;
import xyz.towerdevs.nullifactor.Nullifactor;
import xyz.towerdevs.nullifactor.common.tileentities.TileEntityQuantumReactorContainmentElectromagnet;

public class BlockQuantumReactorContainmentElectromagnet extends BlockQuantumBase {
	public static final BlockQuantumReactorContainmentElectromagnet instance = new BlockQuantumReactorContainmentElectromagnet();
	public BlockQuantumReactorContainmentElectromagnet() {
		super("quantum_reactor_containment_electromagnet", Material.iron, "nullifactor", TileEntityQuantumReactorContainmentElectromagnet.class);
		this.setMultiSidedTexture("nullifactor:quantum_reactor_pylon_side", "nullifactor:quantum_reactor_containment_electromagnet", "nullifactor:quantum_reactor_pylon_top", "nullifactor:quantum_reactor_pylon_top");
		
		this.setCreativeTab(Nullifactor.resourceCreativeTab);
	}
}

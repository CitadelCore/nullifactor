package xyz.towerdevs.nullifactor.blocks;

import net.minecraft.block.material.Material;
import xyz.towerdevs.helios.base.HeliosBlockContainer;
import xyz.towerdevs.nullifactor.Nullifactor;
import xyz.towerdevs.nullifactor.tileentities.TileEntityQuantumReactorContainmentElectromagnet;

public class BlockQuantumReactorContainmentElectromagnet extends HeliosBlockContainer {
	public static final BlockQuantumReactorContainmentElectromagnet instance = new BlockQuantumReactorContainmentElectromagnet();
	public BlockQuantumReactorContainmentElectromagnet() {
		super("quantum_reactor_containment_electromagnet", Material.iron, "nullifactor", TileEntityQuantumReactorContainmentElectromagnet.class);
		this.setMultiSidedTexture("nullifactor:quantum_reactor_pylon_side", "nullifactor:quantum_reactor_containment_electromagnet", "nullifactor:quantum_reactor_pylon_top", "nullifactor:quantum_reactor_pylon_top");
		
		this.setCreativeTab(Nullifactor.resourceCreativeTab);
	}
}

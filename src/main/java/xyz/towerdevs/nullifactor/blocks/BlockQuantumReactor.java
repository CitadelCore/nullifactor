package xyz.towerdevs.nullifactor.blocks;

import net.minecraft.block.material.Material;
import xyz.towerdevs.helios.base.HeliosBlockContainer;
import xyz.towerdevs.nullifactor.Nullifactor;
import xyz.towerdevs.nullifactor.tileentities.TileEntityQuantumReactor;

public class BlockQuantumReactor extends HeliosBlockContainer {

	public static final BlockQuantumReactor instance = new BlockQuantumReactor();
	public BlockQuantumReactor() {
		super("quantum_reactor", Material.iron, "nullifactor", TileEntityQuantumReactor.class);
		this.setMultiSidedTexture("nullifactor:quantum_reactor_side", "nullifactor:quantum_reactor_side", "nullifactor:quantum_reactor_pylon_top", "nullifactor:base_machine_frame");
		
		this.setCreativeTab(Nullifactor.resourceCreativeTab);
	}
}

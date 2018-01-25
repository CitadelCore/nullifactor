package xyz.towerdevs.nullifactor.blocks;

import net.minecraft.block.material.Material;
import xyz.towerdevs.helios.base.HeliosBlockContainer;
import xyz.towerdevs.nullifactor.Nullifactor;
import xyz.towerdevs.nullifactor.tileentities.TileEntityQuantumReactorController;

public class BlockQuantumReactorController extends BlockQuantumBase {
	public static final BlockQuantumReactorController instance = new BlockQuantumReactorController();
	public BlockQuantumReactorController() {
		super("quantum_reactor_controller", Material.iron, "nullifactor", TileEntityQuantumReactorController.class);
		this.setMultiSidedTexture("nullifactor:quantum_reactor_pylon_side", "nullifactor:quantum_reactor_controller", "nullifactor:quantum_reactor_pylon_top", "nullifactor:quantum_reactor_pylon_top");
		
		this.setCreativeTab(Nullifactor.resourceCreativeTab);
	}
}

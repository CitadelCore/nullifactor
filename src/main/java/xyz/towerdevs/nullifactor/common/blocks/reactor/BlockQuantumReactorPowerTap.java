package xyz.towerdevs.nullifactor.common.blocks.reactor;

import net.minecraft.block.material.Material;
import xyz.towerdevs.nullifactor.Nullifactor;
import xyz.towerdevs.nullifactor.common.tileentities.TileEntityQuantumReactorPowerTap;

public class BlockQuantumReactorPowerTap extends BlockQuantumBase {
	public static final BlockQuantumReactorPowerTap instance = new BlockQuantumReactorPowerTap();
	public BlockQuantumReactorPowerTap() {
		super("quantum_reactor_power_tap", Material.iron, "nullifactor", TileEntityQuantumReactorPowerTap.class);
        this.setMultiSidedTexture("nullifactor:quantum_reactor_pylon_side", "nullifactor:quantum_reactor_power_tap", "nullifactor:quantum_reactor_pylon_top", "nullifactor:quantum_reactor_pylon_top");
		
		this.setCreativeTab(Nullifactor.resourceCreativeTab);
	}
}

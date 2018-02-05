package xyz.towerdevs.nullifactor.common.blocks;

import net.minecraft.block.material.Material;
import xyz.towerdevs.nullifactor.Nullifactor;
import xyz.towerdevs.nullifactor.common.tileentities.TileEntityQuantumReactorHeatExchanger;

public class BlockQuantumReactorHeatExchanger extends BlockQuantumBase {
	public static final BlockQuantumReactorHeatExchanger instance = new BlockQuantumReactorHeatExchanger();
	public BlockQuantumReactorHeatExchanger() {
		super("quantum_reactor_heat_exchanger", Material.iron, "nullifactor", TileEntityQuantumReactorHeatExchanger.class);
        this.setMultiSidedTexture("nullifactor:quantum_reactor_pylon_side", "nullifactor:quantum_reactor_heat_exchanger", "nullifactor:quantum_reactor_pylon_top", "nullifactor:quantum_reactor_pylon_top");
		
		this.setCreativeTab(Nullifactor.resourceCreativeTab);
	}

}

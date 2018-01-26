package xyz.towerdevs.nullifactor.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.towerdevs.nullifactor.Nullifactor;
import xyz.towerdevs.nullifactor.tileentities.TileEntityQuantumReactorSingularity;

public class BlockQuantumReactorSingularity extends BlockQuantumBase {
	public static BlockQuantumReactorSingularity instance = new BlockQuantumReactorSingularity();
	public BlockQuantumReactorSingularity() {
		super("quantum_reactor_singularity", Material.iron, "nullifactor", TileEntityQuantumReactorSingularity.class);
		this.setMultiSidedTexture("nullifactor:quantum_reactor_singularity_side", "nullifactor:quantum_reactor_singularity_side", "nullifactor:quantum_reactor_singularity_top", "nullifactor:quantum_reactor_singularity_top");
		
		this.setCreativeTab(Nullifactor.resourceCreativeTab);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int state) {
		TileEntity core = world.getTileEntity(x, y, z);
				
		if (core != null && core instanceof TileEntityQuantumReactorSingularity) {
		}
		
		super.breakBlock(world, x, y, z, block, state);
	}
}

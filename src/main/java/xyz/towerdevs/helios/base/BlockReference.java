package xyz.towerdevs.helios.base;

import net.minecraft.block.Block;

public class BlockReference {
	public Block refBlock;
	public int x;
	public int y;
	public int z;
	
	public BlockReference(Block block, int x, int y, int z) {
		this.refBlock = block;
		this.x = x;
		this.y = y;
		this.z = z;
	}
}

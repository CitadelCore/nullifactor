package xyz.towerdevs.helios.instantiable;

import net.minecraft.tileentity.TileEntity;

public class TileEntityReference {
	public int x;
	public int y;
	public int z;
	
	public TileEntity refTileEntity;
	public TileEntityReference(TileEntity ent, int x, int y, int z) {
		this.refTileEntity = ent;
		this.x = x;
		this.y = y;
		this.z = z;
	}
}

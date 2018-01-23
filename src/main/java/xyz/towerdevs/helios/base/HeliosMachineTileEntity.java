package xyz.towerdevs.helios.base;

import net.minecraft.nbt.NBTTagCompound;

public class HeliosMachineTileEntity extends HeliosTileEntity {
	protected int directionFacing = 2;
	
	public void setFacing(int direction) {
		this.directionFacing = direction;
	}
	
	public int getFacing() {
		return this.directionFacing;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.directionFacing = tag.getShort("MachineFacing");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setShort("MachineFacing", (short) this.directionFacing);
	}
}

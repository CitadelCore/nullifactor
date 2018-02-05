package xyz.towerdevs.helios.base;

import cofh.api.tileentity.IReconfigurableFacing;
import net.minecraft.nbt.NBTTagCompound;

/** Base machine tile entity, from which all machines using the HELIOS framework extend. */
public class HeliosMachineTileEntity extends HeliosTileEntity implements IReconfigurableFacing {
	protected int directionFacing = 2;
	
	public boolean setFacing(int direction) {
		if (direction == 0 || direction == 1)
			return false;
		
		this.directionFacing = (short) direction;
		return true;
	}
	
	public int getFacing() {
		return this.directionFacing;
	}
	
	@Override
	public void hookReadNBT(NBTTagCompound tag) {
		super.hookReadNBT(tag);
		this.directionFacing = tag.getShort("MachineFacing");
	}
	
	@Override
	public void hookWriteNBT(NBTTagCompound tag) {
		super.hookWriteNBT(tag);
		tag.setShort("MachineFacing", (short) this.directionFacing);
	}

	@Override
	public boolean allowYAxisFacing() {
		return false;
	}

	@Override
	public boolean rotateBlock() {
		switch (this.directionFacing) {
		case 3:
			this.setFacing(2);
			break;
		case 2:
			this.setFacing(5);
			break;
		case 5:
			this.setFacing(4);
			break;
		case 4:
			this.setFacing(3);
			break;
		default:
			return false;
		}
		
		return true;
	}
}

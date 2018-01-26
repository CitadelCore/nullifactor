package xyz.towerdevs.nullifactor.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraftforge.common.util.ForgeDirection;
import xyz.towerdevs.nullifactor.items.ItemSingularity;
import xyz.towerdevs.nullifactor.items.ItemSingularity.Singularities;

public class TileEntityQuantumReactorSingularity extends TileEntityQuantumBase implements ISidedInventory, IUpdatePlayerListBox {
	private Singularities singularity;
	private ItemStack singularityStack;
	
	@Override
	public void hookReadNBT(NBTTagCompound nbt) {
		super.hookReadNBT(nbt);
		
		if (nbt.hasKey("SingularityStack")) {
			NBTTagCompound stackCompound = nbt.getCompoundTag("SingularityStack");
			this.singularityStack = ItemStack.loadItemStackFromNBT(stackCompound);
		}
	}
	
	@Override
	public void hookWriteNBT(NBTTagCompound nbt) {
		super.hookWriteNBT(nbt);
		
		if (this.singularityStack != null) {
			NBTTagCompound stackCompound = new NBTTagCompound();
			this.singularityStack.writeToNBT(stackCompound);
			nbt.setTag("SingularityStack", stackCompound);
		}
		
	}
	
	// Singularity frames can't melt.
	@Override
	public void setTemperature(int temp) {
		return;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return singularityStack;
	}

	@Override
	public ItemStack decrStackSize(int slot, int count) {
		ItemStack newStack = this.singularityStack.copy();
		this.singularityStack = null;
		return newStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack stack) {
		this.singularityStack = stack;
	}

	@Override
	public String getInventoryName() {
		return "Singularity Frame";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (stack != null && stack.getItem() instanceof ItemSingularity)
			return true;
		return false;
	}

	@Override
	public void update() {
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		if (dir == ForgeDirection.UP || dir == ForgeDirection.DOWN)
			return new int[] {};
	    return new int[] {1};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int p_102007_3_) {
		if (stack != null && stack.getItem() instanceof ItemSingularity) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int p_102008_3_) {
		return false;
	}
}

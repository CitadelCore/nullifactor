package xyz.towerdevs.nullifactor.tileentities;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraftforge.common.util.ForgeDirection;
import xyz.towerdevs.helios.MathUtilities;
import xyz.towerdevs.nullifactor.items.ItemSingularity;
import xyz.towerdevs.nullifactor.items.ItemSingularity.Singularities;
import xyz.towerdevs.nullifactor.items.ItemSingularity.SingularityData;

public class TileEntityQuantumReactorSingularity extends TileEntityQuantumBase implements ISidedInventory, IUpdatePlayerListBox {
	private SingularityData singularity;
	private ItemStack singularityStack;
	
	/** Fuel in the singularity core, in milibuckets. */
	private float fuelInCore;	
	/**  Max fuel this fuel core may hold. */
	private static int maxFuelInCore = 10000;
	
	/**  Level of spent fuel in the core, in milibuckets. */
	private float spentFuelInCore;
	/**  Max spent fuel this fuel core may hold. */
	private static int maxSpentFuelInCore = 10000;
	
	/**  Whether the fuel core is active. */
	private boolean isActive = false;
	
	private Random random = new Random();
	
	@Override
	public void hookReadNBT(NBTTagCompound nbt) {
		super.hookReadNBT(nbt);
		
		if (nbt.hasKey("SingularityStack")) {
			NBTTagCompound stackCompound = nbt.getCompoundTag("SingularityStack");
			this.singularityStack = ItemStack.loadItemStackFromNBT(stackCompound);
			
			if (this.singularityStack.getItem() instanceof ItemSingularity)
				this.singularity = ((ItemSingularity) this.singularityStack.getItem()).getSingularity();
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
	
	@Override
	public void update() {
		if (!this.worldObj.isRemote) {
			if (this.isActive && this.hasSingularity()) {
				this.convertFuel();
			}
		}
	}
	
	/** Converts quantum or other fuel into waste products. */
	private void convertFuel() {
		if (this.fuelInCore >= this.singularity.fuelPerTick && 
				!((this.spentFuelInCore + this.singularity.fuelPerTick) > TileEntityQuantumReactorSingularity.maxSpentFuelInCore)) {
			
			this.fuelInCore = this.fuelInCore - this.singularity.fuelPerTick;
			this.spentFuelInCore = this.spentFuelInCore + this.singularity.fuelPerTick;
			
			float heatRadiationCoefficient = 1.0F;
			if (MathUtilities.randomBoolean(this.random, this.singularity.quantumEventChance)) {
				heatRadiationCoefficient = 1.5F;
			}
			
			this.radiateHeat(this.singularity.heatPerTick * heatRadiationCoefficient);
		}
	}
	
	/** Inserts the specified amount of quantum fuel into the core. Returns the fuel left over that could not be inserted. */
	private float insertFuel(float fuel) {
		float fuelToInsert = Math.min(TileEntityQuantumReactorSingularity.maxFuelInCore, fuel);
		this.fuelInCore = this.fuelInCore + fuelToInsert;
		
		return fuel - fuelToInsert;
	}
	
	/** Dumps all fuel from the core, which will all be lost. */
	private void removeFuel() {
		this.fuelInCore = 0;
	}
	
	private void radiateHeat(float heat) {
		
	}
	
	/** Returns whether this fuel core has a singularity, and is thus active. */
	public boolean hasSingularity() {
		if (this.singularity != null && this.singularityStack != null)
			return true;
		
		return false;
	}
	
	public boolean getActive() { return this.isActive; }
	public void setActive(boolean isActive) { this.isActive = isActive; }
	
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
		return this.singularityStack;
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
		
		if (this.singularityStack.getItem() instanceof ItemSingularity)
			this.singularity = ((ItemSingularity) this.singularityStack.getItem()).getSingularity();
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

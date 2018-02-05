package xyz.towerdevs.nullifactor.common.tileentities;

import java.util.ArrayList;
import java.util.List;

import cofh.api.tileentity.ITileInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;
import xyz.towerdevs.helios.MathUtilities;
import xyz.towerdevs.helios.registries.HeatConductivity;
import xyz.towerdevs.nullifactor.common.items.ItemSingularity;
import xyz.towerdevs.nullifactor.common.items.ItemSingularity.SingularityData;
import xyz.towerdevs.nullifactor.common.registries.ResourceFluidRegistry;

public class TileEntityQuantumReactorSingularity extends TileEntityQuantumBase implements ISidedInventory, IFluidHandler, ITileInfo {
	private SingularityData singularity;
	private ItemStack singularityStack;
	
	private IFluidTank fuelTank;
	private IFluidTank spentFuelTank;
	
	/**  Max fuel this fuel core may hold. */
	private static int maxFuelInCore = 10000;
	
	/**  Max spent fuel this fuel core may hold. */
	private static int maxSpentFuelInCore = 10000;
	
	/**  Whether the fuel core is active. */
	private boolean isActive = false;
	
	public TileEntityQuantumReactorSingularity() {
		this.canUpdate = true;
		
		this.fuelTank = new FluidTank(maxFuelInCore);
		this.spentFuelTank = new FluidTank(maxSpentFuelInCore);
		
		this.heatConductivity = HeatConductivity.QUANTANIUM;
	}
	
	@Override
	public void hookReadNBT(NBTTagCompound nbt) {
		super.hookReadNBT(nbt);
		
		this.isActive = nbt.getBoolean("IsActive");
		
		if (nbt.hasKey("SingularityStack")) {
			NBTTagCompound stackCompound = nbt.getCompoundTag("SingularityStack");
			this.singularityStack = ItemStack.loadItemStackFromNBT(stackCompound);
			
			if (this.singularityStack.getItem() instanceof ItemSingularity)
				this.singularity = ((ItemSingularity) this.singularityStack.getItem()).getSingularity();
		}
		
		if (nbt.hasKey("FuelStack")) {
			NBTTagCompound stackCompound = nbt.getCompoundTag("FuelStack");
			this.fuelTank.fill(FluidStack.loadFluidStackFromNBT(stackCompound), true);
		}
		
		if (nbt.hasKey("SpentFuelStack")) {
			NBTTagCompound stackCompound = nbt.getCompoundTag("SpentFuelStack");
			this.spentFuelTank.fill(FluidStack.loadFluidStackFromNBT(stackCompound), true);
		}
	}
	
	@Override
	public void hookWriteNBT(NBTTagCompound nbt) {
		super.hookWriteNBT(nbt);
		
		nbt.setBoolean("IsActive", this.isActive);
		
		if (this.singularityStack != null) {
			NBTTagCompound stackCompound = new NBTTagCompound();
			this.singularityStack.writeToNBT(stackCompound);
			nbt.setTag("SingularityStack", stackCompound);
		}
		
		if (this.fuelTank.getFluid() != null) {
			NBTTagCompound stackCompound = new NBTTagCompound();
			this.fuelTank.getFluid().writeToNBT(stackCompound);
			nbt.setTag("FuelStack", stackCompound);
		}
		
		if (this.spentFuelTank.getFluid() != null) {
			NBTTagCompound stackCompound = new NBTTagCompound();
			this.spentFuelTank.getFluid().writeToNBT(stackCompound);
			nbt.setTag("SpentFuelStack", stackCompound);
		}
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if (!this.worldObj.isRemote) {
			if (this.isActive && this.hasSingularity()) {
				this.convertFuel();
			}
		}
	}
	
	/** Converts quantum or other fuel into waste products. */
	private void convertFuel() {
		if (this.fuelTank.getFluidAmount() >= this.singularity.fuelPerTick && 
				!((this.spentFuelTank.getFluidAmount() + this.singularity.fuelPerTick) > TileEntityQuantumReactorSingularity.maxSpentFuelInCore)) {
			
			this.fuelTank.drain((int) this.singularity.fuelPerTick, true);
			this.spentFuelTank.fill(new FluidStack(ResourceFluidRegistry.DEPQUANTUMFUEL.getFluid(), (int) this.singularity.fuelPerTick), true);
			
			float heatRadiationCoefficient = 1.0F;
			if (MathUtilities.randomBoolean(this.singularity.quantumEventChance)) {
				heatRadiationCoefficient = 1.5F;
			}
			
			this.addTemperature(this.singularity.heatPerTick * heatRadiationCoefficient);
		}
	}
	
	/** Dumps all fuel from the core, which will all be lost. */
	public void removeFuel() {
		this.fuelTank.drain(this.fuelTank.getCapacity(), true);
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
	public void setTemperature(double temp) {
		if (this.tileEntityInvalid)
			return;
		
		this.casingTemperature = temp;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot == 0)
			return this.singularityStack;
		
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int count) {
		if (slot != 0)
			return null;
		
		ItemStack newStack = this.singularityStack.copy();
		this.singularityStack = null;
		return newStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if (slot != 0 || stack == null)
			return;
		
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
		if (slot == 0 && stack != null && stack.getItem() instanceof ItemSingularity)
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
		if (slot == 0 && stack != null && stack.getItem() instanceof ItemSingularity) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int p_102008_3_) {
		return false;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (resource.getFluid() == ResourceFluidRegistry.QUANTUMFUEL.getFluid())
			return this.fuelTank.fill(resource, doFill);
		
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (resource.getFluid() == ResourceFluidRegistry.QUANTUMFUEL.getFluid())
			return this.fuelTank.drain(resource.amount, doDrain);
		
		if (resource.getFluid() == ResourceFluidRegistry.DEPQUANTUMFUEL.getFluid())
			return this.spentFuelTank.drain(resource.amount, doDrain);
		
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return this.spentFuelTank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if (fluid == ResourceFluidRegistry.QUANTUMFUEL.getFluid())
			return true;
		
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if (fluid == ResourceFluidRegistry.QUANTUMFUEL.getFluid() || fluid == ResourceFluidRegistry.DEPQUANTUMFUEL.getFluid())
			return true;
		
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		List<FluidTankInfo> tanks = new ArrayList<FluidTankInfo>();
		tanks.add(this.fuelTank.getInfo());
		tanks.add(this.spentFuelTank.getInfo());
		
		return tanks.toArray(new FluidTankInfo[] {});
	}
	
	@Override
    public void getTileInfo(List<IChatComponent> info, ForgeDirection side, EntityPlayer player, boolean debug) {
		 info.add(new ChatComponentText("Quantum fuel: " + this.fuelTank.getFluidAmount() + "/" + this.fuelTank.getCapacity() + "."));
		 info.add(new ChatComponentText("Depleted fuel: " + this.spentFuelTank.getFluidAmount() + "/" + this.spentFuelTank.getCapacity() + "."));
	 }
}

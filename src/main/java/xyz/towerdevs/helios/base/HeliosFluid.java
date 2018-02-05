package xyz.towerdevs.helios.base;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class HeliosFluid extends Fluid {

	protected int colour = 0xFFFFFFFF;
	protected String localizedName;
	
	public HeliosFluid(String fluidName, String localizedName) {
		super(fluidName);
		
		this.localizedName = localizedName;
	}
	
	public HeliosFluid(String fluidName) {
		this(fluidName, null);
	}
	
	@Override
	public int getColor() {
		return this.colour;
	}
	
	@Override
	public final String getLocalizedName(FluidStack stack) {
		return this.localizedName != null ? this.localizedName : super.getLocalizedName(stack);
		
	}
	
	public void setColour(int colour) {
		this.colour = colour;
	}
}

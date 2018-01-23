package xyz.towerdevs.helios.base;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;

public class MultiReturnRecipe extends ShapelessRecipes {
	private ItemStack output;
	
	public MultiReturnRecipe(ItemStack output, @SuppressWarnings("rawtypes") List inputList, ItemStack outputp) {
		super(outputp, inputList);
		this.output = outputp;
	}
	
	@Override
	public ItemStack getRecipeOutput() {
		return this.output;
	}
}

package xyz.towerdevs.helios.helpers;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import xyz.towerdevs.helios.ItemUtilities;

public class CraftingHelper {
	/** Adds recipes for ingots to nuggets and vice versa. */
	public static void addNuggetRecipe(ItemStack ingot, ItemStack nugget) {
		GameRegistry.addShapelessRecipe(ItemUtilities.changeStackAmount(nugget, 9), ingot);
		GameRegistry.addShapelessRecipe(ingot, ItemUtilities.duplicateStack(nugget, 9).toArray());
	}
	
	/** Adds recipes for blocks to ingots and vice versa. */
	public static void addBlockRecipe(ItemStack block, ItemStack ingot) {
		GameRegistry.addShapelessRecipe(ItemUtilities.changeStackAmount(ingot, 9), block);
		GameRegistry.addShapelessRecipe(block, ItemUtilities.duplicateStack(ingot, 9).toArray());
	}
}

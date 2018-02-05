package xyz.towerdevs.helios;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import xyz.towerdevs.helios.base.HeliosItem;

public class ItemUtilities {
	public static HeliosItem newResourceItem(String unlocalizedName, String textureName, Class<? extends HeliosItem> itemClass) {
		HeliosItem resItem;
		try {
			resItem = itemClass.newInstance();
			
			resItem.setUnlocalizedName(unlocalizedName);
			resItem.setTextureName(textureName);
			
			return resItem;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static HeliosItem newResourceItem(String unlocalizedName, String textureName, Class<? extends HeliosItem> itemClass, CreativeTabs tab) {
		HeliosItem resItem = newResourceItem(unlocalizedName, textureName, itemClass);
		resItem.setCreativeTab(tab);
		return resItem;
	}
	
	/** Duplicates a stack, useful for shapeless recipes. */
	public static List<ItemStack> duplicateStack(ItemStack stack, int times) {
		List<ItemStack> stackList = new ArrayList<ItemStack>();
		
		for (int i = 0; i < times; i++) {
			stackList.add(stack.copy());
		}
		
		return stackList;
	}
	
	/** Changes the amount of items in a stack while keeping the item damage. */
	public static ItemStack changeStackAmount(ItemStack stack, int amount) {
		return new ItemStack(stack.getItem(), amount, stack.getItemDamage());
	}
}

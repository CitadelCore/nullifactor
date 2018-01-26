package xyz.towerdevs.helios;

import net.minecraft.creativetab.CreativeTabs;
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
}

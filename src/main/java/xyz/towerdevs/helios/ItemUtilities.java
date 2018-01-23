package xyz.towerdevs.helios;

import net.minecraft.creativetab.CreativeTabs;
import xyz.towerdevs.helios.base.HeliosItem;

public class ItemUtilities {
	public static HeliosItem newResourceItem(String unlocalizedName, String textureName) {
		HeliosItem resItem = new HeliosItem();
		resItem.setUnlocalizedName(unlocalizedName);
		resItem.setTextureName(textureName);
		
		return resItem;
	}
	
	public static HeliosItem newResourceItem(String unlocalizedName, String textureName, CreativeTabs tab) {
		HeliosItem resItem = newResourceItem(unlocalizedName, textureName);
		resItem.setCreativeTab(tab);
		return resItem;
	}
}

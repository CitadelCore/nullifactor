package xyz.towerdevs.helios.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class HeliosBlockMultiTex extends HeliosBlock {
	private List<String> textures = new ArrayList<String>();
	private HashMap<String, IIcon> icons = new HashMap<String, IIcon>();
	
	public HeliosBlockMultiTex(String unlocalizedName, Material blockMaterial, String modId) {
		super(unlocalizedName, blockMaterial, modId);
	}
	
	public void addDamageTexture(String texture) {
		textures.add(texture);
	}
	
	protected IIcon getTextureForDamage(int damage) {
		return icons.get(textures.get(damage));
	}
}

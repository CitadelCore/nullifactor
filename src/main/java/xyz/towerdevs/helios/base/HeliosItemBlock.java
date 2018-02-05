package xyz.towerdevs.helios.base;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class HeliosItemBlock extends ItemBlock {
	private String informationText = null;
	private boolean hasEnchantmentEffect = false;

	public HeliosItemBlock(Block block) {
		super(block);
		
		this.registerInformationText();
	}
	
	public void registerInformationText(String text) {
		this.informationText = text;
	}
	
	public void registerInformationText() {
		String text = StatCollector.translateToLocal(this.getUnlocalizedName() + ".tooltip");
		String compText = this.getUnlocalizedName() + ".tooltip";
		if (text != null && !text.isEmpty() && !text.equals(compText))
			registerInformationText(text);
	}
	
	public void registerEnchantmentEffect() {
		this.hasEnchantmentEffect = true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack, int renderPass) {
		return this.hasEnchantmentEffect;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack item, EntityPlayer player, @SuppressWarnings("rawtypes") List tooltip, boolean isSpecial) {
		if (this.informationText != null)
			tooltip.add(this.informationText);
	}
}

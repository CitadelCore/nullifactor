package xyz.towerdevs.helios.base;
import xyz.towerdevs.helios.registries.SoundRegistry;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.render.IHaloRenderItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.EnumHelper;

public class HeliosItem extends Item implements IHaloRenderItem {
	public static final EnumRarity rarityUltimate = EnumHelper.addRarity("ULTIMATE", EnumChatFormatting.AQUA, "Ultimate");
	public static enum AvaritiaHaloType { REGULAR, NOISE }
	
	protected SoundRegistry soundRegistry;
	protected EnumRarity rarity = EnumRarity.common;
	private boolean isAnimated = false;
	private boolean isMulticolour = false;
	
	private String foregroundAnim;
	private String backgroundAnim;
	private IIcon foregroundIcon;
	private IIcon backgroundIcon;
	
	private int primaryColour = 0;
	private int secondaryColour = 0;
	
	// Avaritia Halo
	public boolean hasAvaritiaHalo = false;
	private String haloTexture;
	private IIcon haloTextureIcon;
	private int haloSize = 4;
	private boolean haloPulsing = false;
	private int haloColour = 0xFF000000;
	
	// Misc
	private String informationText = null;
	private boolean hasEnchantmentEffect = false;
	
	public HeliosItem() {
		this.soundRegistry = new SoundRegistry();
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return this.rarity;
	}
	
	public void setRarity(EnumRarity rarity) {
		this.rarity = rarity;
	}
	
	public void registerItemAnimation(String fg, String bg) {
		this.foregroundAnim = fg;
		this.backgroundAnim = bg;
		this.isAnimated = true;
	}
	
	public void registerItemMulticolour(int primary, int secondary) {
		this.primaryColour = primary;
		this.secondaryColour = secondary;
		this.isMulticolour = true;
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
	
	private void registerAvaritiaSpecialHalo(int haloSize, boolean haloPulsing, int haloColour, String haloTexture) {
		this.haloSize = haloSize;
		this.haloPulsing = haloPulsing;
		this.haloColour = haloColour;
		this.haloTexture = haloTexture;
		
		this.hasAvaritiaHalo = true;
	}
	
	public void registerAvaritiaSpecialHalo(int haloSize, boolean haloPulsing, int haloColour, AvaritiaHaloType haloType) {
		switch(haloType) {
		case REGULAR:
			registerAvaritiaSpecialHalo(haloSize, haloPulsing, haloColour, "avaritia:halo");
			break;
		case NOISE:
			registerAvaritiaSpecialHalo(haloSize, haloPulsing, haloColour, "avaritia:halonoise");
			break;
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack, int renderPass) {
		return this.hasEnchantmentEffect;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		if (this.isMulticolour) {
			if (this.primaryColour != 0 && renderPass == 0)
				return this.primaryColour;
			
			if (this.secondaryColour != 0 && renderPass != 0)
				return this.secondaryColour;
		}
		
		return 0xFFFFFFF;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List tooltip, boolean isSpecial) {
		if (this.informationText != null)
			tooltip.add(this.informationText);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register) {
		if (this.isAnimated) {
			this.foregroundIcon = register.registerIcon(this.foregroundAnim);
			this.backgroundIcon = register.registerIcon(this.backgroundAnim);
		}
		
		if (this.hasAvaritiaHalo)
			this.haloTextureIcon = register.registerIcon(this.haloTexture);
		
		super.registerIcons(register);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		if (this.isAnimated) {
			if (pass == 0)
				return this.backgroundIcon;
			
			return this.foregroundIcon;
		}
		
		return super.getIcon(stack, pass);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean requiresMultipleRenderPasses() {
		return (this.isAnimated || this.isMulticolour);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean drawHalo(ItemStack stack) {
		return this.hasAvaritiaHalo;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean drawPulseEffect(ItemStack stack) {
		return this.haloPulsing;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getHaloColour(ItemStack stack) {
		return this.haloColour;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getHaloSize(ItemStack stack) {
		return this.haloSize;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getHaloTexture(ItemStack stack) {
		return this.haloTextureIcon;
	}
}

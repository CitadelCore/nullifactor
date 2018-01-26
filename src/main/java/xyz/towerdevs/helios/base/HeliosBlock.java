package xyz.towerdevs.helios.base;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xyz.towerdevs.helios.registries.SoundRegistry;
import xyz.towerdevs.nullifactor.misc.NullifactorAchievementRegistry;

public class HeliosBlock extends Block {
protected SoundRegistry soundRegistry;
    protected IIcon SideTexture;
	protected IIcon FrontTexture;
	protected IIcon TopTexture;
	protected IIcon BottomTexture;
	
	protected IIcon DefaultTexture;
	
    private String SideTextureId, FrontTextureId, TopTextureId, BottomTextureId, DefaultTextureId;
    protected boolean useSidedTextures = false;
	
    private NullifactorAchievementRegistry brokenAchievement = null;
	public HeliosBlock(String unlocalizedName, Material blockMaterial, String modId) {
		super(blockMaterial);
		this.soundRegistry = new SoundRegistry();
		this.setBlockName(unlocalizedName);
		//this.setBlockTextureName(modId + ":" + unlocalizedName);
		this.DefaultTextureId = modId + ":" + unlocalizedName;
	}
	
	public void setMultiSidedTexture(String side, String front, String top, String bottom) {
		this.SideTextureId = side;
		this.FrontTextureId = front;
		this.TopTextureId = top;
		this.BottomTextureId = bottom;
		
		this.useSidedTextures = true;
	}
	
	public void setBrokenAchievement(NullifactorAchievementRegistry achOverride) {
		this.brokenAchievement = achOverride;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int state) {
		super.breakBlock(world, x, y, z, block, state);
	}
	
	@Override
	public void onBlockHarvested(World world, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer player) {
		if (this.brokenAchievement != null)
			this.brokenAchievement.trigger(player);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		if (this.useSidedTextures) {
			this.SideTexture = iconRegister.registerIcon(this.SideTextureId);
			this.FrontTexture = iconRegister.registerIcon(this.FrontTextureId);
			this.TopTexture = iconRegister.registerIcon(this.TopTextureId);
			this.BottomTexture = iconRegister.registerIcon(this.BottomTextureId);
		}
		
		this.DefaultTexture = iconRegister.registerIcon(this.DefaultTextureId);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		if (this.useSidedTextures == true) {
			if (side == 0)
				return this.BottomTexture;
			if (side == 1)
				return this.TopTexture;
			if (side == 3)
				return this.FrontTexture;
			
			return this.SideTexture;
		}
		
		return this.DefaultTexture;
	}
}

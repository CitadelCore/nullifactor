package xyz.towerdevs.nullifactor.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import xyz.towerdevs.helios.base.HeliosGui;
import xyz.towerdevs.nullifactor.containers.ContainerReactorSingularity;

public class GuiReactorSingularityContainer extends HeliosGui {

	public GuiReactorSingularityContainer(IInventory inventory, TileEntity core) {
		super(new ContainerReactorSingularity(inventory, core));
		
		this.xSize = 176;
		this.ySize = 198;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("nullifactor", "textures/gui/quantum_reactor_singularity.png"));
		int mhor = (this.width - this.xSize) / 2;
		int mver = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(mhor, mver - 32, 0, 0, this.xSize, this.ySize);
	}
}

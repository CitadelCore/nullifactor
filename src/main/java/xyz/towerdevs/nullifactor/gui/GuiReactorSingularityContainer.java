package xyz.towerdevs.nullifactor.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import xyz.towerdevs.nullifactor.containers.ContainerReactorSingularity;
import xyz.towerdevs.nullifactor.tileentities.TileEntityQuantumReactorSingularity;

public class GuiReactorSingularityContainer extends GuiContainer {

	private InventoryPlayer player;
	private TileEntityQuantumReactorSingularity core;
	public GuiReactorSingularityContainer(InventoryPlayer player, TileEntityQuantumReactorSingularity core) {
		super(new ContainerReactorSingularity(player, core));
		this.player = player;
		this.core = core;
		
		this.xSize = 176;
		this.ySize = 198;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(new ResourceLocation("nullifactor", "textures/gui/quantum_reactor_singularity.png"));
		int mhor = (this.width - this.xSize) / 2;
		int mver = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(mhor, mver - 32, 0, 0, this.xSize, this.ySize);
	}
}

package xyz.towerdevs.nullifactor.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import xyz.towerdevs.helios.base.HeliosGui;
import xyz.towerdevs.nullifactor.common.containers.ContainerReactorController;
import xyz.towerdevs.nullifactor.common.tileentities.TileEntityQuantumReactorController;

public class GuiReactorController extends HeliosGui {
	private TileEntityQuantumReactorController controller;
	
	public GuiReactorController(IInventory inventory, TileEntity controller) {
		super(new ContainerReactorController(inventory, controller));
		
		this.controller = (TileEntityQuantumReactorController) controller;
		
		this.xSize = 176;
		this.ySize = 198;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("nullifactor", "textures/gui/quantum_base.png"));
		int mhor = (this.width - this.xSize) / 2;
		int mver = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(mhor, mver - 32, 0, 0, this.xSize, this.ySize);
		
		// Render RF bar
		int rf = (int) ((((double) this.controller.getEnergyStored(ForgeDirection.UNKNOWN)) / (double) this.controller.getMaxEnergyStored(ForgeDirection.UNKNOWN)) * 112);
		mhor = (this.width - 166) / 2;
		mver = (this.height - 260) / 2;
		
		this.drawTexturedModalRect(mhor, mver, 192, 0, 14, 112);
		this.drawTexturedModalRect(mhor, mver, 177, 0, 14, rf);
		
		if (this.isMouseInRect(mhor, mver, 14, 111, mouseX, mouseY)) {
			List<String> text = new ArrayList<String>();
			text.add("RF stored: ");
			text.add(this.controller.getEnergyStored(ForgeDirection.UNKNOWN) + "/" + this.controller.getMaxEnergyStored(ForgeDirection.UNKNOWN));
			this.drawHoveringText(text, mouseX, mouseY, this.fontRendererObj);
		}
	}
}

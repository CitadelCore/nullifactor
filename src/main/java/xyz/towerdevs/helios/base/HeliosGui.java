package xyz.towerdevs.helios.base;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public abstract class HeliosGui extends GuiContainer {
	public HeliosGui(Container container) {
		super(container);
	}
	
	/** Returns whether the mouse is inside the bounds of the specified coordinates. */
	protected boolean isMouseInRect(int x, int y, int width, int height, int mouseX, int mouseY) {
		return ((mouseX >= x && mouseX <= x + width) && (mouseY >= y && mouseY < y + height));
	}
}

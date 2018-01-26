package xyz.towerdevs.nullifactor.registries;

import net.minecraft.client.gui.Gui;
import net.minecraft.inventory.Container;
import xyz.towerdevs.nullifactor.gui.*;
import xyz.towerdevs.nullifactor.containers.*;

public enum GuiRegistry {
	SINGULARITY(GuiReactorSingularityContainer.class, ContainerReactorSingularity.class),
	;
	
	public static final GuiRegistry[] guiRegistry = values();
	private Class<? extends Gui> gui;
	private Class<? extends Container> container;
	private GuiRegistry(Class<? extends Gui> gui, Class<? extends Container> container) {
		this.gui = gui;
		this.container = container;
	}
	
	private GuiRegistry(Class<? extends Gui> gui) {
		this(gui, null);
	}
	
	public Class<? extends Gui> getGui() {
		return this.gui;
	}
	
	public Class<? extends Container> getContainer() {
		return this.container;
	}
}

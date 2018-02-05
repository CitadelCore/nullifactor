package xyz.towerdevs.nullifactor.common.registries;

import net.minecraft.client.gui.Gui;
import net.minecraft.inventory.Container;
import xyz.towerdevs.nullifactor.client.gui.GuiReactorController;
import xyz.towerdevs.nullifactor.client.gui.GuiReactorSingularityContainer;
import xyz.towerdevs.nullifactor.common.containers.*;

public enum GuiRegistry {
	SINGULARITY(GuiReactorSingularityContainer.class, ContainerReactorSingularity.class),
	CONTROLLER(GuiReactorController.class, ContainerReactorController.class),
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

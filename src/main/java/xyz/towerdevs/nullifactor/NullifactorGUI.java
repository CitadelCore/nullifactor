package xyz.towerdevs.nullifactor;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import xyz.towerdevs.nullifactor.containers.ContainerReactorSingularity;
import xyz.towerdevs.nullifactor.gui.GuiReactorSingularityContainer;
import xyz.towerdevs.nullifactor.tileentities.TileEntityQuantumReactorSingularity;

public class NullifactorGUI implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == 0)
			return new ContainerReactorSingularity(player.inventory, (TileEntityQuantumReactorSingularity)world.getTileEntity(x, y, z));
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == 0)
			return new GuiReactorSingularityContainer(player.inventory, (TileEntityQuantumReactorSingularity)world.getTileEntity(x, y, z));
		return null;
	}
	
}

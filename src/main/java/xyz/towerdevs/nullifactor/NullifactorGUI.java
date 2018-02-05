package xyz.towerdevs.nullifactor;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import xyz.towerdevs.nullifactor.common.registries.GuiRegistry;

public class NullifactorGUI implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (GuiRegistry.values().length >= ID) {
			try {
				return GuiRegistry.values()[ID].getContainer().getConstructor(IInventory.class, TileEntity.class).newInstance(new Object[] { player.inventory, world.getTileEntity(x, y, z) });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (GuiRegistry.values().length >= ID) {
			try {
				return GuiRegistry.values()[ID].getGui().getConstructor(IInventory.class, TileEntity.class).newInstance(new Object[] { player.inventory, world.getTileEntity(x, y, z) });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
}

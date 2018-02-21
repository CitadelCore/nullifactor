package xyz.towerdevs.nullifactor.common.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import xyz.towerdevs.nullifactor.common.entities.EntityMorko;

public class CommandMorko extends CommandBase {

	@Override
	public String getCommandName() {
		return "spawnmorko";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "spawnmorko";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		ChunkCoordinates coords = sender.getPlayerCoordinates();
		
		Entity ent = new EntityMorko(sender.getEntityWorld());
		ent.setPosition(coords.posX, coords.posY, coords.posZ);
		sender.getEntityWorld().spawnEntityInWorld(ent);
	}
	
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
    	World world = sender.getEntityWorld();
    	EntityPlayer player = world.getPlayerEntityByName(sender.getCommandSenderName());
    	if (player == null)
    		return false;
    	
    	if (player.capabilities.isCreativeMode)
    		return true;
    	
    	return false;
    }
}

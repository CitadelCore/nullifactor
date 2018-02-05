package xyz.towerdevs.helios.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import xyz.towerdevs.helios.base.HeliosTileEntity;
import xyz.towerdevs.helios.network.NetworkUtilities;

/** Class which handles sending different variable types between the client and server. */
public class TileDataPacket implements IMessage {
	short index;
	short packetType;
	
	int x;
	int y;
	int z;
	Object data;
	
	/** Empty constructor for netty instantiation. */
	public TileDataPacket() { }
	
	public TileDataPacket(HeliosTileEntity tileEntity, int index, int packetType, Object data) {
		this.index = (short) index;
		this.packetType = (short) packetType;
		this.data = data;
		
		this.x = tileEntity.xCoord;
		this.y = tileEntity.yCoord;
		this.z = tileEntity.zCoord;
	}
	
	public static class Handler implements IMessageHandler<TileDataPacket, IMessage> {
		@Override
		public IMessage onMessage(TileDataPacket message, MessageContext ctx) {
			if (ctx.side == Side.CLIENT) {
				TileEntity entity = Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);
				
				if (!(entity instanceof HeliosTileEntity))
					return null;
				
				((HeliosTileEntity) entity).recieveServerPacket(message.index, message.data);
			} else {
				TileEntity entity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
				
				if (!(entity instanceof HeliosTileEntity))
					return null;
				
				((HeliosTileEntity) entity).recieveClientPacket(message.index, message.data);
			}
			
			return null;
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.index = buf.readByte();
		this.packetType = buf.readByte();
		this.data = NetworkUtilities.deserializeObjectFromBuffer(this.packetType, buf);
		
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(this.index);
		buf.writeByte(this.packetType);
		NetworkUtilities.serializeObjectToBuffer(this.packetType, buf, this.data);
		
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
	}

}

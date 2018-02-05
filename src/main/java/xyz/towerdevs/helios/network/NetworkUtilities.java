package xyz.towerdevs.helios.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import xyz.towerdevs.helios.network.NetworkDataType;

/** Class for handling network functions and doing a lot of the dirty work. */
public class NetworkUtilities {
	
	/** Takes a ByteBuf buffer, an object and a packet type, 
	 * and writes the object into the buffer. */
	public static void serializeObjectToBuffer(int packetType, ByteBuf buf, Object object) {
		switch (packetType) {
		case NetworkDataType.TYPE_INT:
			buf.writeInt((Integer) object);
			break;
		case NetworkDataType.TYPE_SHORT:
			buf.writeShort((Short) object);
			break;
		case NetworkDataType.TYPE_LONG:
			buf.writeLong((Long) object);
			break;
		case NetworkDataType.TYPE_FLOAT:
			buf.writeFloat((Float) object);
			break;
		case NetworkDataType.TYPE_DOUBLE:
			buf.writeDouble((Double) object);
			break;
		case NetworkDataType.TYPE_CHAR:
			buf.writeChar((Character) object);
			break;
		case NetworkDataType.TYPE_BYTE:
			buf.writeByte((Byte) object);
			break;
		case NetworkDataType.TYPE_STRING:
			ByteBufUtils.writeUTF8String(buf, (String) object);
			break;
		case NetworkDataType.TYPE_BOOL:
			buf.writeBoolean((Boolean) object);
			break;
		default:
			break;
		}
	}
	
	/** Takes a packet type and byte buffer, and returns an Object with the specified dataType, 
	 * deserialized from the packet buffer. */
	public static Object deserializeObjectFromBuffer(int packetType, ByteBuf buf) {
		switch (packetType) {
		case NetworkDataType.TYPE_INT:
			return buf.readInt();
		case NetworkDataType.TYPE_SHORT:
			return buf.readShort();
		case NetworkDataType.TYPE_LONG:
			return buf.readLong();
		case NetworkDataType.TYPE_FLOAT:
			return buf.readFloat();
		case NetworkDataType.TYPE_DOUBLE:
			return buf.readDouble();
		case NetworkDataType.TYPE_CHAR:
			return buf.readChar();
		case NetworkDataType.TYPE_BYTE:
			return buf.readByte();
		case NetworkDataType.TYPE_STRING:
			return ByteBufUtils.readUTF8String(buf);
		case NetworkDataType.TYPE_BOOL:
			return buf.readBoolean();
		default:
			return null;
		}
	}
}

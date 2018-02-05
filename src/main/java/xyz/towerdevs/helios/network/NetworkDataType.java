package xyz.towerdevs.helios.network;

/** Constants of data types that are used in networking packets. 
 * Primarily used in the TileDataPacket class for tile entity interop. */
public class NetworkDataType {
	public static final byte TYPE_INT = 0;
	public static final byte TYPE_SHORT = 1;
	public static final byte TYPE_LONG = 2;
	public static final byte TYPE_FLOAT = 3;
	public static final byte TYPE_DOUBLE = 4;
	public static final byte TYPE_CHAR = 5;
	public static final byte TYPE_BYTE = 6;
	public static final byte TYPE_STRING = 7;
	public static final byte TYPE_BOOL = 8;
}

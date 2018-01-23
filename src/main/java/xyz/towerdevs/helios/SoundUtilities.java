package xyz.towerdevs.helios;

import net.minecraft.util.ResourceLocation;

public final class SoundUtilities {
	/** Retrieves an ResourceLocation instance from a HELIOS mod repository and a sound resource ID. */
	public static ResourceLocation GetSoundFromMod(String mod, String sound) {
		return new ResourceLocation(mod + ":" + sound);
	}
}

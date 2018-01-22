package xyz.towerdevs.helios;

import cpw.mods.fml.common.Mod;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import xyz.towerdevs.nullifactor.Nullifactor;

public final class SoundUtilities {
	/** Retrieves an ResourceLocation instance from a HELIOS mod repository and a sound resource ID. */
	public static ResourceLocation GetSoundFromMod(String mod, String sound) {
		return new ResourceLocation(mod + ":" + sound);
	}
}

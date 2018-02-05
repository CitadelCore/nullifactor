package xyz.towerdevs.helios.registries;

import java.util.HashMap;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import xyz.towerdevs.helios.SoundUtilities;

public class SoundRegistry {
	private HashMap<String, ISound> sounds = new HashMap<String, ISound>();
	private HashMap<String, ResourceLocation> soundResources = new HashMap<String, ResourceLocation>();
	public static SoundHandler soundHandler;
	
	public void RegisterSound(String resource, ISound sound) {
		this.sounds.put(resource, sound);
	}
	
	public void RegisterResource(String mod, String resource) {
		ResourceLocation soundRef = SoundUtilities.GetSoundFromMod(mod, resource);
		this.soundResources.put(resource, soundRef);
	}
	
	public ISound getSound(String resource) { return this.sounds.get(resource); }
	
	public HashMap<String, ISound> getRegistry() { return this.sounds; }
	
	public void flushAllSounds() {
		soundHandler.stopSounds();
		this.sounds.clear();
	}
	
	public void stopSound(String resource) {
		if (this.soundResources.containsKey(resource) && this.sounds.containsKey(resource)) {
			ISound currsound = this.sounds.get(resource);
			soundHandler.stopSound(currsound);
			this.sounds.remove(resource);
		}
	}
	
	public void playSound(String resource, boolean allowRepeat) {
		if (soundHandler != null && this.soundResources.containsKey(resource) && (!this.sounds.containsKey(resource) || allowRepeat)) {
			ResourceLocation soundRef = this.soundResources.get(resource);
			
			ISound newSound = PositionedSoundRecord.func_147673_a(soundRef);
			soundHandler.playSound(newSound);
			
			if (this.sounds.containsKey(resource)) {
				this.sounds.remove(resource);
			}
			
			this.sounds.put(resource, newSound);
		}
	}
	
	public void playSound(String resource) {
		playSound(resource, false);
	}
}

package xyz.towerdevs.helios.registries;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block.SoundType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import xyz.towerdevs.helios.HeliosMod;
import xyz.towerdevs.helios.SoundUtilities;

public class SoundRegistry {
	private HashMap<String, ISound> sounds = new HashMap<String, ISound>();
	private HashMap<String, ResourceLocation> soundResources = new HashMap<String, ResourceLocation>();
	private SoundHandler soundHandler;
	
	public SoundRegistry() {
		this.soundHandler = Minecraft.getMinecraft().getSoundHandler();
	}
	
	public void RegisterSound(String resource, ISound sound) {
		sounds.put(resource, sound);
	}
	
	public void RegisterResource(String mod, String resource) {
		ResourceLocation soundRef = SoundUtilities.GetSoundFromMod(mod, resource);
		soundResources.put(resource, soundRef);
	}
	
	public ISound getSound(String resource) { return sounds.get(resource); }
	
	public HashMap<String, ISound> getRegistry() { return sounds; }
	
	public void flushAllSounds() {
		soundHandler.stopSounds();
		sounds.clear();
	}
	
	public void stopSound(String resource) {
		if (soundResources.containsKey(resource) && sounds.containsKey(resource)) {
			ResourceLocation soundRef = soundResources.get(resource);		
			
			ISound currsound = sounds.get(resource);
			soundHandler.stopSound(currsound);
			sounds.remove(resource);
		}
	}
	
	public void playSound(String resource, boolean allowRepeat) {
		if (soundResources.containsKey(resource) && (!sounds.containsKey(resource) || allowRepeat)) {
			ResourceLocation soundRef = soundResources.get(resource);
			
			ISound newSound = PositionedSoundRecord.func_147673_a(soundRef);
			soundHandler.playSound(newSound);
			
			if (sounds.containsKey(resource)) {
				sounds.remove(resource);
			}
			
			sounds.put(resource, newSound);
		}
	}
	
	public void playSound(String resource) {
		playSound(resource, false);
	}
}

package xyz.towerdevs.helios.base;
import xyz.towerdevs.helios.registries.SoundRegistry;

import net.minecraft.item.Item;

public class HeliosItem extends Item {
	protected SoundRegistry soundRegistry;
	
	public HeliosItem() {
		this.soundRegistry = new SoundRegistry();
	}
}

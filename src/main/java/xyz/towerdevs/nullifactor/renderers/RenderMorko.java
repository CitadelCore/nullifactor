package xyz.towerdevs.nullifactor.renderers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import xyz.towerdevs.nullifactor.Nullifactor;

public class RenderMorko extends RenderLiving {
	protected ResourceLocation morkoTexture;
	
	public RenderMorko(ModelBase base, float shadowSize) {
		super(base, shadowSize);
		this.morkoTexture = new ResourceLocation(Nullifactor.MODID + ":textures/entity/morko.png");
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.morkoTexture;
	}
}

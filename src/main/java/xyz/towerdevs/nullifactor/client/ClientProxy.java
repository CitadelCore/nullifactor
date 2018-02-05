package xyz.towerdevs.nullifactor.client;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import xyz.towerdevs.helios.registries.SoundRegistry;
import xyz.towerdevs.nullifactor.client.renderers.RenderMorko;
import xyz.towerdevs.nullifactor.common.CommonProxy;
import xyz.towerdevs.nullifactor.common.entities.EntityMorko;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		
		RenderingRegistry.registerEntityRenderingHandler(EntityMorko.class, new RenderMorko(new ModelBiped(), 0.4F));
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
		
		SoundRegistry.soundHandler = Minecraft.getMinecraft().getSoundHandler();
	}
}

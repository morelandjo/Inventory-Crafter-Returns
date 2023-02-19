package io._3650.inventory_crafter.client;

import io._3650.inventory_crafter.InventoryCrafter;
import io._3650.inventory_crafter.registry.config.Config;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class InventoryCrafterClient {
	
	public static final ResourceLocation BUTTON = new ResourceLocation(InventoryCrafter.MOD_ID, "textures/gui/button.png");
	public static ImageButton inventoryButton;
	static boolean buttonPendingReload = false;
	
	public InventoryCrafterClient() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(this::registerKeybinds);
		bus.addListener(this::configLoad);
		bus.addListener(this::configReload);
	}
	
	public void registerKeybinds(RegisterKeyMappingsEvent event) {
		ModKeybinds.init(event);
	}
	
	public void configLoad(ModConfigEvent.Loading event) {
		if (event.getConfig().getSpec() == Config.CLIENT_SPEC) {
			loadPreset(Config.CLIENT.preset.get());
		}
	}
	
	public void configReload(ModConfigEvent.Reloading event) {
		if (event.getConfig().getSpec() == Config.CLIENT_SPEC) {
			loadPreset(Config.CLIENT.preset.get());
			if (inventoryButton != null) buttonPendingReload = true;
		}
	}
	
	private void loadPreset(String preset) {
		switch (Config.CLIENT.preset.get()) {
		case "NONE":
			break;
		case "DEFAULT":
			Config.CLIENT.buttonLeftX.set(Config.Presets.PDefault.X);
			Config.CLIENT.buttonTopY.set(Config.Presets.PDefault.Y);
			Config.CLIENT.preset.set("NONE");
			break;
		case "CLOSE":
			Config.CLIENT.buttonLeftX.set(Config.Presets.PClose.X);
			Config.CLIENT.buttonTopY.set(Config.Presets.PClose.Y);
			Config.CLIENT.preset.set("NONE");
			break;
		case "GRID":
			Config.CLIENT.buttonLeftX.set(Config.Presets.PGrid.X);
			Config.CLIENT.buttonTopY.set(Config.Presets.PGrid.Y);
			Config.CLIENT.preset.set("NONE");
			break;
		case "BAD":
			Config.CLIENT.buttonLeftX.set(Config.Presets.PBad.X);
			Config.CLIENT.buttonTopY.set(Config.Presets.PBad.Y);
			Config.CLIENT.preset.set("NONE");
			break;
		default:
			Config.CLIENT.preset.set("NONE");
			break;
		}
	}
	
}
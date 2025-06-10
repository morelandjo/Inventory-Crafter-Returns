package io._3650.inventory_crafter.client;

import io._3650.inventory_crafter.InventoryCrafter;
import io._3650.inventory_crafter.registry.config.Config;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

public class InventoryCrafterClient {
	
	public static final ResourceLocation BUTTON = ResourceLocation.fromNamespaceAndPath(InventoryCrafter.MOD_ID, "textures/gui/button.png");
	public static Button inventoryButton;
	static boolean buttonPendingReload = false;
	
	public InventoryCrafterClient(IEventBus modEventBus) {
		modEventBus.addListener(this::registerKeybinds);
		modEventBus.addListener(this::configLoad);
		modEventBus.addListener(this::configReload);
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
		case "BOOK":
			Config.CLIENT.buttonLeftX.set(Config.Presets.PBook.X);
			Config.CLIENT.buttonTopY.set(Config.Presets.PBook.Y);
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
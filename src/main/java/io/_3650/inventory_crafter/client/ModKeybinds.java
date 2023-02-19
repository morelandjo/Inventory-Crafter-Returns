package io._3650.inventory_crafter.client;

import com.mojang.blaze3d.platform.InputConstants;

import io._3650.inventory_crafter.InventoryCrafter;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;

public class ModKeybinds {
	
	public static final KeyMapping INVENTORY_CRAFTING = createKeybind("inventory_crafting", KeyConflictContext.UNIVERSAL, InputConstants.KEY_V, KeyMapping.CATEGORY_INVENTORY);
	
	public static void init(RegisterKeyMappingsEvent event) {
		event.register(INVENTORY_CRAFTING);
	}
	
	private static KeyMapping createKeybind(String name, KeyConflictContext context, int keycode, String category) {
		return new KeyMapping("key." + InventoryCrafter.MOD_ID + "." + name, context, InputConstants.Type.KEYSYM, keycode, category);
	}
	
	public static boolean isKeyPressed(KeyMapping key) {
		return key != null && InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), key.getKey().getValue());
	}
	
}
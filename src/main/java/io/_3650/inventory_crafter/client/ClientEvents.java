package io._3650.inventory_crafter.client;

import io._3650.inventory_crafter.InventoryCrafter;
import io._3650.inventory_crafter.mixin.AbstractContainerScreenAccessor;
import io._3650.inventory_crafter.network.InventoryCrafterPacket;
import io._3650.inventory_crafter.network.NetworkHandler;
import io._3650.inventory_crafter.registry.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(modid = InventoryCrafter.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
	
	@SubscribeEvent
	public static void onClientTickEnd(ClientTickEvent.Post event) {
		if (Minecraft.getInstance().level != null) {
			while (ModKeybinds.INVENTORY_CRAFTING.consumeClick()) {
				System.out.println("InventoryCrafter: Keybind pressed, sending packet");
				sendOpenPacket();
			}
		}
	}
	
	@SubscribeEvent
	public static void onScreenRender(ScreenEvent.Render.Post event) {
		Minecraft minecraft = Minecraft.getInstance();
		if (InventoryCrafterClient.inventoryButton == null || minecraft.player == null || minecraft.level == null || !(minecraft.screen instanceof InventoryScreen screen)) return;
		
		if (minecraft.level.getGameTime() % 5 == 0) {
			InventoryCrafterClient.inventoryButton.visible = InventoryCrafter.hasCraftingTable(minecraft.player);
			if (InventoryCrafterClient.buttonPendingReload) {
				int leftPos = ((AbstractContainerScreenAccessor)screen).getLeftPos();
				int topPos = ((AbstractContainerScreenAccessor)screen).getTopPos();
				InventoryCrafterClient.inventoryButton.setPosition(leftPos + Config.CLIENT.buttonLeftX.get(), topPos + Config.CLIENT.buttonTopY.get());
				InventoryCrafterClient.buttonPendingReload = false;
			}
		}
	}
	
	private static void sendOpenPacket() {
		System.out.println("InventoryCrafter: Sending packet to server");
		NetworkHandler.sendToServer(new InventoryCrafterPacket());
	}
	
}
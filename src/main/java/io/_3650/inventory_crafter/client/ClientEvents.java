package io._3650.inventory_crafter.client;

import io._3650.inventory_crafter.InventoryCrafter;
import io._3650.inventory_crafter.mixin.AbstractContainerScreenAccessor;
import io._3650.inventory_crafter.network.InventoryCrafterPacket;
import io._3650.inventory_crafter.network.NetworkHandler;
import io._3650.inventory_crafter.registry.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ContainerScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = InventoryCrafter.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
	
	@SubscribeEvent
	public static void onKeyPress(ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END && Minecraft.getInstance().level != null) {
			while (ModKeybinds.INVENTORY_CRAFTING.consumeClick()) {
				sendOpenPacket();
			}
		}
	}
	
//	@SubscribeEvent
//	public static void onScreenInit(InitScreenEvent.Post event) {
//		Minecraft minecraft = Minecraft.getInstance();
//		if (minecraft.player == null || !(minecraft.screen instanceof InventoryScreen screen)) return;
//		
//		int leftPos = ((AbstractContainerScreenAccessor)screen).getLeftPos();
//		int topPos = ((AbstractContainerScreenAccessor)screen).getTopPos();
//		
//		InventoryCrafterClient.inventoryButton = new ImageButton(leftPos + Config.CLIENT.buttonLeftX.get(), topPos + Config.CLIENT.buttonTopY.get(), 20, 18, 0, 0, 19, InventoryCrafterClient.BUTTON, clicked -> {
//			Minecraft minceraft = Minecraft.getInstance(); //resource leak warning wont shut if I do it in one line
//			if (InventoryCrafter.hasCraftingTable(minceraft.player)) sendOpenPacket();
//		});
//		
//		InventoryCrafterClient.inventoryButton.visible = InventoryCrafter.hasCraftingTable(minecraft.player);
//		((ScreenInvoker)screen).callAddRenderableWidget(InventoryCrafterClient.inventoryButton);
//	}
	
	@SubscribeEvent
	public static void onGuiRender(ContainerScreenEvent.Render.Background event) {
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
		NetworkHandler.sendToServer(new InventoryCrafterPacket());
	}
	
}
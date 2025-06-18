package io._3650.inventory_crafter.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io._3650.inventory_crafter.InventoryCrafter;
import io._3650.inventory_crafter.client.InventoryCrafterClient;
import io._3650.inventory_crafter.network.InventoryCrafterPacket;
import io._3650.inventory_crafter.network.NetworkHandler;
import io._3650.inventory_crafter.registry.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
	public InventoryScreenMixin(T menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
	}
	
	@Inject(method = "init", at = @At("TAIL"))
	private void inventory_crafter_init(CallbackInfo ci) {
		System.out.println("InventoryCrafter: InventoryScreenMixin init called");
		Minecraft minecraft = Minecraft.getInstance();
		
		boolean hasCraftingTable = InventoryCrafter.hasCraftingTable(minecraft.player);
		System.out.println("InventoryCrafter: Player has crafting table: " + hasCraftingTable);
		
		int leftPos = ((AbstractContainerScreenAccessor)this).getLeftPos();
		int topPos = ((AbstractContainerScreenAccessor)this).getTopPos();
		int buttonX = leftPos + Config.CLIENT.buttonLeftX.get();
		int buttonY = topPos + Config.CLIENT.buttonTopY.get();
		
		System.out.println("InventoryCrafter: Button position: " + buttonX + ", " + buttonY + " (config: " + Config.CLIENT.buttonLeftX.get() + ", " + Config.CLIENT.buttonTopY.get() + ")");
		
		InventoryCrafterClient.inventoryButton = Button.builder(Component.empty(), clicked -> {
			System.out.println("InventoryCrafter: Button clicked!");
			if (InventoryCrafter.hasCraftingTable(minecraft.player)) {
				System.out.println("InventoryCrafter: Sending packet to server");
				NetworkHandler.sendToServer(new InventoryCrafterPacket());
			} else {
				System.out.println("InventoryCrafter: Player doesn't have crafting table");
			}		})
		.bounds(buttonX, buttonY, 20, 18)
		.build(builder -> new Button(builder) {
			@Override			protected void renderWidget(net.minecraft.client.gui.GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
				int x = getX();
				int y = getY();
				boolean hovered = isHoveredOrFocused();
				
				// Debug output
				System.out.println("InventoryCrafter: Button render - x=" + x + ", y=" + y + ", hovered=" + hovered);
				
				// Render the texture
				try {
					int textureY = hovered ? 19 : 0;
					// Use the correct blit signature: button is 20x18, hover at y=19, sheet is 256x256
					guiGraphics.blit(net.minecraft.client.renderer.RenderType::guiTextured, InventoryCrafterClient.BUTTON, x, y, 0, textureY, 20, 18, 256, 256);
				} catch (Exception e) {
					// Fallback rendering if texture fails
					System.out.println("InventoryCrafter: Texture rendering failed: " + e.getMessage());
					guiGraphics.fill(x, y, x + getWidth(), y + getHeight(), isHoveredOrFocused() ? 0xFFFF0000 : 0xFF888888);
				}
			}
		});
		
		InventoryCrafterClient.inventoryButton.visible = hasCraftingTable;
		System.out.println("InventoryCrafter: Button visible: " + InventoryCrafterClient.inventoryButton.visible);
		
		this.addRenderableWidget(InventoryCrafterClient.inventoryButton);
		System.out.println("InventoryCrafter: Button added to screen");
	}
}
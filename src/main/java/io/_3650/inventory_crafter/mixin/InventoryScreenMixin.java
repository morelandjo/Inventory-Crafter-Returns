package io._3650.inventory_crafter.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
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
	
	@Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;", shift = Shift.AFTER))
	private void inventory_crafter_init(CallbackInfo ci) {
		InventoryCrafterClient.inventoryButton = Button.builder(Component.empty(), clicked -> {
			Minecraft minecraft = Minecraft.getInstance();
			if (InventoryCrafter.hasCraftingTable(minecraft.player)) {
				NetworkHandler.sendToServer(new InventoryCrafterPacket());
			}
		})
		.bounds(leftPos + Config.CLIENT.buttonLeftX.get(), topPos + Config.CLIENT.buttonTopY.get(), 20, 18)
		.build(builder -> new Button(builder) {
			@Override
			protected void renderWidget(net.minecraft.client.gui.GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
				int x = getX();
				int y = getY();
				int textureY = isHoveredOrFocused() ? 19 : 0;
				guiGraphics.blit(InventoryCrafterClient.BUTTON, x, y, 0, textureY, 20, 18);
			}
		});
		
		InventoryCrafterClient.inventoryButton.visible = InventoryCrafter.hasCraftingTable(minecraft.player);
		this.addRenderableWidget(InventoryCrafterClient.inventoryButton);
	}

	@Inject(method = "(Lnet/minecraft/client/gui/components/Button;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/Button;setPosition(II)V", shift = Shift.AFTER))
	private void inventory_crafter_moveButton(Button button, CallbackInfo cb) {
		if (InventoryCrafterClient.inventoryButton != null) InventoryCrafterClient.inventoryButton.setPosition(this.leftPos + Config.CLIENT.buttonLeftX.get(), this.topPos + Config.CLIENT.buttonTopY.get());
	}
	
}
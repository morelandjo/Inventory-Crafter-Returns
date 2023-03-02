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
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
	
	//dummy constructor
	public InventoryScreenMixin(T menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
	}
	
	@Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;", shift = Shift.AFTER))
	private void inventory_crafter_init(CallbackInfo ci) {
		InventoryCrafterClient.inventoryButton = new ImageButton(leftPos + Config.CLIENT.buttonLeftX.get(), topPos + Config.CLIENT.buttonTopY.get(), 20, 18, 0, 0, 19, InventoryCrafterClient.BUTTON, clicked -> {
			Minecraft minceraft = Minecraft.getInstance(); //resource leak warning wont shut if I do it in one line
			if (InventoryCrafter.hasCraftingTable(minceraft.player)) NetworkHandler.sendToServer(new InventoryCrafterPacket());
		});
		
		InventoryCrafterClient.inventoryButton.visible = InventoryCrafter.hasCraftingTable(minecraft.player);
		this.addRenderableWidget(InventoryCrafterClient.inventoryButton);
	}

	@Inject(method = "(Lnet/minecraft/client/gui/components/Button;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ImageButton;setPosition(II)V", shift = Shift.AFTER))
	private void inventory_crafter_moveButton(Button button, CallbackInfo cb) {
		if (InventoryCrafterClient.inventoryButton != null) InventoryCrafterClient.inventoryButton.setPosition(this.leftPos + Config.CLIENT.buttonLeftX.get(), this.topPos + Config.CLIENT.buttonTopY.get());
	}
	
}
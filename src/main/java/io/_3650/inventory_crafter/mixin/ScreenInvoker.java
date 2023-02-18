package io._3650.inventory_crafter.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;

@Mixin(Screen.class)
public interface ScreenInvoker {
	
	@Invoker("addRenderableWidget")
	public <T extends GuiEventListener & Widget & NarratableEntry> T callAddRenderableWidget(T pWidget);
	
}

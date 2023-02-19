package io._3650.inventory_crafter;

import io._3650.inventory_crafter.client.InventoryCrafterClient;
import io._3650.inventory_crafter.menu.PersistentCraftingMenu;
import io._3650.inventory_crafter.network.NetworkHandler;
import io._3650.inventory_crafter.registry.ModItemTags;
import io._3650.inventory_crafter.registry.config.Config;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(InventoryCrafter.MOD_ID)
public class InventoryCrafter {
	
	public static final String MOD_ID = "inventory_crafter";
	public static final Component CRAFTING_TABLE_TITLE =Component.translatable("container.inventory_crafter.inventory_crafting");
	
	public InventoryCrafter() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		bus.addListener(this::setup);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> InventoryCrafterClient::new);
		
		ModLoadingContext.get().registerConfig(Type.CLIENT, Config.CLIENT_SPEC, "inventory_crafter-client.toml");
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			NetworkHandler.init();
		});
	}
	
	public static boolean hasCraftingTable(Player player) {
		return player.getInventory().contains(ModItemTags.CRAFTING_TABLE);
	}
	
	public static void openCrafting(ServerPlayer player) {
		if (!hasCraftingTable(player)) return;
		player.closeContainer();
		player.openMenu(new SimpleMenuProvider((containerId, inv, p) -> new PersistentCraftingMenu(containerId, inv, ContainerLevelAccess.create(p.level, p.blockPosition())), CRAFTING_TABLE_TITLE));
	}
	
}

package io._3650.inventory_crafter;

import io._3650.inventory_crafter.client.InventoryCrafterClient;
import io._3650.inventory_crafter.menu.PersistentCraftingMenu;
import io._3650.inventory_crafter.registry.ModItemTags;
import io._3650.inventory_crafter.registry.config.Config;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(InventoryCrafter.MOD_ID)
public class InventoryCrafter {
	
	public static final String MOD_ID = "inventory_crafter";
	public static final Component CRAFTING_TABLE_TITLE = Component.translatable("container.inventory_crafter.inventory_crafting");
	
	public InventoryCrafter(IEventBus modEventBus, ModContainer modContainer) {
		modEventBus.addListener(this::setup);
		
		// Register config
		modContainer.registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC, "inventory_crafter-common.toml");
		modContainer.registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC, "inventory_crafter-client.toml");
		
		// Initialize client-side components on client
		if (FMLEnvironment.dist == Dist.CLIENT) {
			new InventoryCrafterClient(modEventBus);
		}
	}
	
	private void setup(final FMLCommonSetupEvent event) {
		// Setup is now handled via event subscription for networking
	}
	
	public static boolean hasCraftingTable(Player player) {
		boolean requireTable = Config.COMMON.requireCraftingTable.get();
		
		if (!requireTable) {
			return true;
		}
		
		boolean hasTable = player.getInventory().items.stream()
			.anyMatch(stack -> !stack.isEmpty() && (
				stack.is(ModItemTags.CRAFTING_TABLE) || 
				stack.is(net.minecraft.world.item.Items.CRAFTING_TABLE)
			));
		
		return hasTable;
	}
	
	public static void openCrafting(ServerPlayer player) {
		System.out.println("InventoryCrafter: Checking if player has crafting table...");
		if (!hasCraftingTable(player)) {
			System.out.println("InventoryCrafter: Player does not have crafting table, aborting");
			return;
		}
		System.out.println("InventoryCrafter: Player has crafting table, opening menu...");
		player.closeContainer();
		player.openMenu(new SimpleMenuProvider((containerId, inv, p) -> new PersistentCraftingMenu(containerId, inv, ContainerLevelAccess.create(p.level(), p.blockPosition())), CRAFTING_TABLE_TITLE));
		System.out.println("InventoryCrafter: Menu opened successfully");
	}
	
	@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			event.enqueueWork(() -> {
				// This will be called on the main thread during client setup
			});
		}
	}
}

package io._3650.inventory_crafter.network;

import io._3650.inventory_crafter.InventoryCrafter;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {

	private static final String PROTOCOL_VERSION = "1.0.0";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(InventoryCrafter.MOD_ID, "network"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals);
	
	private static int id = 0;
	
	public static void init() {
		INSTANCE.registerMessage(id++, InventoryCrafterPacket.class, InventoryCrafterPacket::encode, InventoryCrafterPacket::decode, InventoryCrafterPacket::handle);
	}
	
	public static <MSG> void sendToServer(MSG msg) {
		INSTANCE.sendToServer(msg);
	}
	
}
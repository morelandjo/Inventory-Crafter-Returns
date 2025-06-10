package io._3650.inventory_crafter.network;

import io._3650.inventory_crafter.InventoryCrafter;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = InventoryCrafter.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkHandler {

	@SubscribeEvent
	public static void register(RegisterPayloadHandlersEvent event) {
		System.out.println("InventoryCrafter: Registering network packets");
		PayloadRegistrar registrar = event.registrar("1");
		registrar.playToServer(
			InventoryCrafterPacket.TYPE,
			InventoryCrafterPacket.STREAM_CODEC,
			InventoryCrafterPacket::handle
		);
		System.out.println("InventoryCrafter: Network packets registered successfully");
	}
	
	public static void sendToServer(CustomPacketPayload payload) {
		PacketDistributor.sendToServer(payload);
	}
	
}
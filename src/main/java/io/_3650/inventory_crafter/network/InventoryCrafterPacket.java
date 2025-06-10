package io._3650.inventory_crafter.network;

import io._3650.inventory_crafter.InventoryCrafter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record InventoryCrafterPacket() implements CustomPacketPayload {
	
	public static final Type<InventoryCrafterPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(InventoryCrafter.MOD_ID, "open_crafting"));
	
	public static final StreamCodec<FriendlyByteBuf, InventoryCrafterPacket> STREAM_CODEC = 
		StreamCodec.unit(new InventoryCrafterPacket());
	
	@Override
	public Type<InventoryCrafterPacket> type() {
		return TYPE;
	}
	
	public static void handle(InventoryCrafterPacket packet, IPayloadContext context) {
		System.out.println("InventoryCrafter: Packet received on server");
		context.enqueueWork(() -> {
			if (context.player() instanceof ServerPlayer serverPlayer) {
				System.out.println("InventoryCrafter: Opening crafting for player " + serverPlayer.getName().getString());
				InventoryCrafter.openCrafting(serverPlayer);
			}
		});
	}
	
}
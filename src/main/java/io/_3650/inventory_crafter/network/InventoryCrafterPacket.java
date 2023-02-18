package io._3650.inventory_crafter.network;

import java.util.function.Supplier;

import io._3650.inventory_crafter.InventoryCrafter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public record InventoryCrafterPacket() {
	
	public static void encode(InventoryCrafterPacket packet, FriendlyByteBuf buffer) {
		//nothing to store
	}
	
	public static InventoryCrafterPacket decode(FriendlyByteBuf buffer) {
		return new InventoryCrafterPacket();
	}
	
	public static void handle(InventoryCrafterPacket packet, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayer player = ctx.get().getSender();
			if (player == null || player.level.isClientSide) return;
			InventoryCrafter.openCrafting(player);
		});
		ctx.get().setPacketHandled(true);
	}
	
}